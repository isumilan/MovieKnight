package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;

import com.mysql.jdbc.Driver;

import models.MovieEvent;
import models.Profile;

public class SQLDriver {
	
	private final static String selectName = "SELECT * FROM USERS WHERE USERNAME=?";
	private final static String addUser = "INSERT INTO USERS(USERNAME,PASSWORD,ZIPCODE) VALUES(?,?,?)";
	private final static String findPassword = "SELECT PASSWORD FROM USERS WHERE USERNAME=?";
	private final static String selectEvent = "SELECT * FROM MOVIEEVENTS WHERE EVENTID=?";
	private final static String selectParticipants = "SELECT USERNAME FROM EVENTPARTICIPANTS WHERE EVENTID=? AND ACCEPTED=?";
	private final static String addEvent = "INSERT INTO MOVIEEVENTS(EVENTID,OWNER,MOVIEID,DESCRIPTION,MOVIETIME,THEATER) VALUES(?,?,?,?,?,?)";
	private final static String addParticipants = "INSERT INTO EVENTPARTICIPANTS(P_ID,EVENTID,ACCEPTED,USERNAME) VALUES(?,?,?,?)";
	private final static String sendFriendRequest = "INSERT INTO FRIENDS(P_ID,ACCEPTED,SENDER,RECEIVER) VALUES(?,?,?,?)";
	private final static String acceptFriendRequest = "UPDATE FRIENDS SET ACCEPTED=? WHERE SENDER=? AND RECEIVER=?";
	private final static String denyFriendRequest = "DELETE FROM FRIENDS WHERE SENDER=? AND RECEIVER=?";
	private final static String sendEventInvite = "INSERT INTO EVENTPARTICIPANTS(P_ID,EVENTID,ACCEPTED,USERNAME) VALUES(?,?,?,?)";
	private final static String acceptEventInvite = "UPDATE EVENTPARTICIPANTS SET ACCEPTED=? WHERE EVENTID=? AND USERNAME=?";
	private final static String denyEventInvite = "DELETE FROM EVENTPARTICIPANTS WHERE EVENTID=? AND USERNAME=?";
	private final static String addMovieToList = "INSERT INTO MOVIELISTS(P_ID,LIST_TYPE,USERNAME,MOVIEID) VALUES=(?,?,?,?)";
	private final static String editDescription = "UPDATE USERS SET DESCRIPTION=? WHERE USERNAME=?";
	
	private Connection con;
	private ServerLog log;
	
	public SQLDriver(ServerLog inLog) {
		log = inLog;
		try {
			new Driver();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieknight_database?user=root&password=root");
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() { return con; }
	
	public void stop() {
		try {
			con.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean doesExist(String user) {
		try {
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, user);
			ResultSet result = ps.executeQuery();
			if (!result.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean RegisterRequest(String username, String password, int zipcode) {
		if (!doesExist(username)) {
			try {
				PreparedStatement ps = con.prepareStatement(addUser);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setInt(3, zipcode);
				ps.executeUpdate();
				log.write("Registered " + username);
				return true;
			} catch (SQLException e) {
				log.write("Registration failed");
				return false;
			}
		} else {
			log.write(username + " is taken");
			return false;
		}
	}
	
	public boolean LoginRequest(String username, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(findPassword);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			if (result.next() && result.getString(1).equals(password)) {
				log.write("Logged in " + username);
				return true;
			}
		} catch (SQLException e) {
			log.write(username + " failed to log in");
		}
		return false;
	}
	
	public Profile getProfile(String username) {
		Profile user = new Profile();
		try {
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				user.setUsername(result.getString(1));
				user.setProfilePicture(result.getString(3));
				user.setDescription(result.getString(4));
				user.setZipcode(result.getInt(5));
			}
		} catch (SQLException e) {
			log.write("Failed to send profile with: " + username);
		}
		log.write("Sent profile with: " + username);
		return user;
	}
	
	public MovieEvent getMovieEvent(String eventID){
		MovieEvent event = new MovieEvent();
		try {
			PreparedStatement ps = con.prepareStatement(selectEvent);
			ps.setString(1, eventID);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				event.setEventID(result.getString(1));
				event.setOwner(result.getString(2));
				event.setGoingToWatch(result.getInt(3));
				event.setDescription(result.getString(4));
				event.setMovieTime(result.getString(5));
				event.setTheater(result.getString(6));
				event.setInvited(getEventParticipants(eventID, false));
				event.setParticipants(getEventParticipants(eventID, true));
			}
		} catch (SQLException e) {
			log.write("Failed to send event with ID: " + eventID);
		}
		log.write("Sent event with ID: " + eventID);
		return event;
	}
	
	public Vector<String> getEventParticipants(String eventID, boolean accepted) {
		Vector<String> participants = new Vector<String>();
		try {
			PreparedStatement ps = con.prepareStatement(selectParticipants);
			ps.setString(1, eventID);
			ps.setBoolean(2, accepted);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				participants.add(result.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return participants;
	}
	
	public boolean MakeMovieEvent(MovieEvent event){
		try {
			PreparedStatement ps = con.prepareStatement(addEvent);
			ps.setString(1, event.getEventID());
			ps.setString(2, event.getOwner());
			ps.setInt(3, event.getGoingToWatch());
			ps.setString(4, event.getDescription());
			ps.setString(5, event.getMovieTime());
			ps.setString(6, event.getTheater());
			ps.executeUpdate();
			
			PreparedStatement ps2 = con.prepareStatement(addParticipants);
			for (String invitee : event.getInvited()) {
				ps2.setString(1, UUID.randomUUID().toString());
				ps2.setString(2, event.getEventID());
				ps2.setBoolean(3, false);
				ps2.setString(4, invitee);
				ps2.executeUpdate();
			}
			
			PreparedStatement ps3 = con.prepareStatement(addParticipants);
			for (String participant : event.getInvited()) {
				ps3.setString(1, UUID.randomUUID().toString());
				ps3.setString(2, event.getEventID());
				ps3.setBoolean(3, true);
				ps3.setString(4, participant);
				ps3.executeUpdate();
			}
			
			log.write("Made event with ID: " + event.getEventID());
			return true;
		} catch (SQLException e) {
			log.write("Failed to make event with ID: " + event.getEventID());
			return false;
		}
	}

	public boolean FriendRequest(String sender, String receiver) {
		try {
			PreparedStatement ps = con.prepareStatement(sendFriendRequest);
			ps.setString(1, UUID.randomUUID().toString());
			ps.setBoolean(2, true);
			ps.setString(3, sender);
			ps.setString(4, receiver);
			ps.executeUpdate();
			log.write("Sent friend request from " + sender + " to " + receiver);
			return true;
		} catch (SQLException e) {
			log.write("Failed to send friend request");
			return false;
		}
	}
	
	public boolean AcceptFriend(String sender, String receiver, boolean reply){
		try {
			PreparedStatement ps;
			if (reply) {
				ps = con.prepareStatement(acceptFriendRequest);
				ps.setBoolean(1, true);
				ps.setString(2, sender);
				ps.setString(3, receiver);
			} else {
				ps = con.prepareStatement(denyFriendRequest);
				ps.setString(1, sender);
				ps.setString(2, receiver);
			}
			ps.executeUpdate();
			log.write(receiver + " replied to friend request");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			log.write("Failed to reply to friend request");
			return false;
		}
	}
	
	public boolean EventInvite(String eventID, String username){
		try {
			PreparedStatement ps = con.prepareStatement(sendEventInvite);
			ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, eventID);
			ps.setBoolean(3, false);
			ps.setString(4, username);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean EventReply(String eventID, String username, boolean reply){
		try {
			PreparedStatement ps;
			if (reply) {
				ps = con.prepareStatement(acceptEventInvite);
				ps.setBoolean(1, true);
				ps.setString(2, eventID);
				ps.setString(3, username);
			} else {
				ps = con.prepareStatement(denyEventInvite);
				ps.setString(1, eventID);
				ps.setString(2, username);
			}
			ps.executeUpdate();
			log.write(username + " replied to event invite");
			return true;
		} catch (SQLException e) {
			log.write("Failed to reply to event invite");
			return false;
		}
	}
	
	public boolean AddToList(String list_type, int movieID, String name) {
		try {
			PreparedStatement ps= con.prepareStatement(addMovieToList);
			ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, list_type);
			ps.setString(3, name);
			ps.setInt(4, movieID);
			ps.executeUpdate();
			log.write(movieID + " added to " + list_type + " list of " + name);
			return true;
		} catch (SQLException e) {
			log.write("Failed to add movie to list");
			return false;
		}
	}
	
	public boolean EditDescription(String description, String username) {
		try {
			PreparedStatement ps= con.prepareStatement(editDescription);
			ps.setString(1, description);
			ps.setString(2, username);
			ps.executeUpdate();
			log.write(username + " edited description");
			return true;
		} catch (SQLException e) {
			log.write("Failed to edit description");
			return false;
		}
	}
}
