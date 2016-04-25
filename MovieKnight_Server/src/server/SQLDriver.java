package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;

import com.example.nathan.movieknight.models.MovieEvent;
import com.example.nathan.movieknight.models.Profile;
import com.mysql.jdbc.Driver;

public class SQLDriver {
	
	private final static String selectName = "SELECT * FROM USERS WHERE USERNAME=?";
	private final static String addUser = "INSERT INTO USERS(USERNAME,PASSWORD,ZIPCODE) VALUES(?,?,?)";
	private final static String findPassword = "SELECT PASSWORD FROM USERS WHERE USERNAME=?";
	private final static String selectEvent = "SELECT * FROM MOVIEEVENTS WHERE EVENTID=?";
	private final static String selectParticipants = "SELECT USERNAME FROM EVENTPARTICIPANTS WHERE EVENTID=? AND ACCEPTED=?";
	private final static String addEvent = "INSERT INTO MOVIEEVENTS(EVENTID,OWNER,MOVIEID,EVENTTITLE,PUBLIC_PRIVATE,MOVIETIME,THEATER) VALUES(?,?,?,?,?,?,?)";
	private final static String addParticipants = "INSERT INTO EVENTPARTICIPANTS(EVENTID,ACCEPTED,USERNAME) VALUES(?,?,?)";
	private final static String sendFriendRequest = "INSERT INTO FRIENDS(ACCEPTED,SENDER,RECEIVER) VALUES(?,?,?)";
	private final static String acceptFriendRequest = "UPDATE FRIENDS SET ACCEPTED=? WHERE SENDER=? AND RECEIVER=?";
	private final static String denyFriendRequest = "DELETE FROM FRIENDS WHERE SENDER=? AND RECEIVER=?";
	private final static String sendEventInvite = "INSERT INTO EVENTPARTICIPANTS(EVENTID,ACCEPTED,USERNAME) VALUES(?,?,?)";
	private final static String acceptEventInvite = "UPDATE EVENTPARTICIPANTS SET ACCEPTED=? WHERE EVENTID=? AND USERNAME=?";
	private final static String denyEventInvite = "DELETE FROM EVENTPARTICIPANTS WHERE EVENTID=? AND USERNAME=?";
	private final static String addMovieToList = "INSERT INTO MOVIELISTS(LIST_TYPE,USERNAME,MOVIEID) VALUES(?,?,?)";
	private final static String editDescription = "UPDATE USERS SET DESCRIPTION=? WHERE USERNAME=?";
	private final static String allUsers = "SELECT * FROM USERS";
	private final static String setHasNewRequest = "UPDATE USERS SET HASNEWREQUEST=? WHERE USERNAME=?";
	private final static String setHasNewInvite = "UPDATE USERS SET HASNEWINVITE=? WHERE USERNAME=?";
	
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
	
	public Profile RegisterRequest(String username, String password, int zipcode) {
		if (!doesExist(username)) {
			try {
				PreparedStatement ps = con.prepareStatement(addUser);
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setInt(3, zipcode);
				ps.executeUpdate();
				log.write("Registered " + username);
				return LoginRequest(username, password);
			} catch (SQLException e) {
				log.write("Registration failed");
				return null;
			}
		} else {
			log.write(username + " is taken");
			return null;
		}
	}
	
	public Profile LoginRequest(String username, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(findPassword);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			if (result.next() && result.getString(1).equals(password)) {
				log.write("Logged in " + username);
				return getProfile(username);
			}
		} catch (SQLException e) {
			log.write(username + " failed to log in");
		}
		return null;
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
			user.setFriends(this.getFriendsList(username));
			user.setFriendRequests(this.getFriendsRequestList(username));
			user.setToWatch(this.getMovieList("towatch", username));
			user.setWatched(this.getMovieList("watched", username));
			user.setLiked(this.getMovieList("liked", username));
			user.setEvents(this.getParticipatingEvents(username));
			user.setEventRequests(this.getInvitedEvents(username));
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
				event.setPublic_private(result.getBoolean(5));
				event.setMovieTime(result.getString(6));
				event.setTheater(result.getString(7));
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
	
	public MovieEvent MakeMovieEvent(MovieEvent event){
		try {
			PreparedStatement ps = con.prepareStatement(addEvent);
			ps.setString(1, event.getEventID());
			ps.setString(2, event.getOwner());
			ps.setInt(3, event.getGoingToWatch());
			ps.setString(4, event.getDescription());
			ps.setBoolean(5,  event.isPublic_private());
			ps.setString(6, event.getMovieTime());
			ps.setString(7, event.getTheater());
			ps.executeUpdate();
			
			PreparedStatement ps2 = con.prepareStatement(addParticipants);
			for (String invitee : event.getInvited()) {
				ps2.setString(1, event.getEventID());
				ps2.setBoolean(2, false);
				ps2.setString(3, invitee);
				ps2.executeUpdate();
				
				PreparedStatement ps3 = con.prepareStatement(setHasNewInvite);
				ps3.setBoolean(1, true);
				ps3.setString(2, invitee);
				ps3.executeUpdate();
			}
			
		 	PreparedStatement ps3 = con.prepareStatement(addParticipants);
			for (String participant : event.getParticipants()) {
				ps3.setString(1, event.getEventID());
				ps3.setBoolean(2, true);
				ps3.setString(3, participant);
				ps3.executeUpdate();
			}
		 
			log.write("Made event with ID: " + event.getEventID());
			return event;
		} catch (SQLException e) {
			e.printStackTrace();
			log.write("Failed to make event with ID: " + event.getEventID());
			return null;
		}
	}

	public boolean FriendRequest(String sender, String receiver) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM  friends WHERE sender=? AND receiver=?");
 			ps.setString(1, sender);
 			ps.setString(2, receiver);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){
				ps = con.prepareStatement(sendFriendRequest);
				ps.setBoolean(1, false);
				ps.setString(2, sender);
				ps.setString(3, receiver);
				ps.executeUpdate();
				
				PreparedStatement ps2 = con.prepareStatement(setHasNewRequest);
				ps2.setBoolean(1, true);
				ps2.setString(2, receiver);
				ps2.executeUpdate();
				
				log.write("Sent friend request from " + sender + " to " + receiver);
				return true;
			}
		} catch (SQLException e) {
			log.write("Failed to send friend request");
			return false;
		}
		return false;
	}
	
	public boolean AcceptFriend(String sender, String receiver, boolean reply){
		try {
			PreparedStatement ps;
			if (reply) {
				ps = con.prepareStatement(acceptFriendRequest);
				ps.setBoolean(1, true);
				ps.setString(2, receiver);
				ps.setString(3, sender);
			} else {
				ps = con.prepareStatement(denyFriendRequest);
				ps.setString(1, receiver);
				ps.setString(2, sender);
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
			ps.setString(1, eventID);
			ps.setBoolean(2, false);
			ps.setString(3, username);
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
			PreparedStatement ps = con.prepareStatement("SELECT * FROM  movielists WHERE list_type=? AND username=? AND movieID=?");
 			ps.setString(1, list_type);
 			ps.setString(2, name);
 			ps.setInt(3, movieID);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){			
				ps = con.prepareStatement(addMovieToList);
				System.out.println(list_type+" "+movieID+" "+name);
				ps.setString(1, UUID.randomUUID().toString());
				ps.setString(2, list_type);
				ps.setString(3, name);
				ps.setInt(4, movieID);
				ps.executeUpdate();
				log.write(movieID + " added to " + list_type + " list of " + name);
			}
 			return true;
 		} catch (SQLException e) {
 			e.printStackTrace();
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
	
	public Vector<String> ListAllUsers(){
		Vector<String> names = new Vector<String>();
		try {
			PreparedStatement ps = con.prepareStatement(allUsers);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				names.add(rs.getString("username"));
			}
			log.write("Sent list of all users");
			return names;
		} catch (SQLException e) {
			log.write("Failed to send list of all users");
			return null;
		}
	}
	
	public boolean HasSeenRequests(String username) {
		try {
			boolean thereIs = true;
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1,username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				thereIs = rs.getBoolean("hasNewRequest");
				System.out.println("Friend: " + thereIs);
			}
			log.write("Check if " + username + " has new friend requests");
			ps = con.prepareStatement(setHasNewRequest);
			ps.setBoolean(1, false);
			ps.setString(2, username);
			ps.executeUpdate();
			log.write("Changed requests to seen " + thereIs);
			return thereIs;
		} catch (SQLException e) {
			log.write("Failed to change requests to seen");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean HasSeenInvites(String username) {
		try {
			boolean thereIs = false;
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				thereIs = rs.getBoolean("hasNewInvite");
				System.out.println("event:" + thereIs);
			}
			log.write("Check if " + username + " has new event invite");
			ps = con.prepareStatement(setHasNewInvite);
			ps.setBoolean(1, false);
			ps.setString(2, username);
			ps.executeUpdate();
			log.write("Changed invites to seen " + thereIs);
			return thereIs;
		} catch (SQLException e) {
			log.write("Failed to change requests to seen");
			return false;
		}
	}
	
	private Vector<String> getFriendsList(String username){
		Vector<String> friends = new Vector<String>();
		try {
			PreparedStatement ps= con.prepareStatement("SELECT * FROM friends "
					+ "WHERE accepted=1 AND (sender=? OR receiver=?)");
			ps.setString(1, username);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("sender").equals(username)){
					friends.add(rs.getString("receiver"));
				}else{
					friends.add(rs.getString("sender"));
				}
			}
			return friends;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	private Vector<String> getFriendsRequestList(String username){
		Vector<String> friends = new Vector<String>();
		try {
			PreparedStatement ps= con.prepareStatement("SELECT * FROM friends "
					+ "WHERE accepted=0 AND receiver=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				friends.add(rs.getString("sender"));
			}
			return friends;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
	
	private Vector<Integer> getMovieList(String listType, String username){
		Vector<Integer> movies = new Vector<Integer>();
		try {
			PreparedStatement ps= con.prepareStatement("SELECT * FROM movielists "
					+ "WHERE username=? AND list_type=?");
			ps.setString(1, username);
			ps.setString(2, listType);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				movies.add(rs.getInt("movieID"));
			}
			return movies;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}
	
	private Vector<String> getParticipatingEvents(String username){
		Vector<String> events = new Vector<String>();
		try {
			PreparedStatement ps= con.prepareStatement("SELECT * FROM eventparticipants "
					+ "WHERE accepted=1 AND username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				events.add(rs.getString("eventID"));
			}
			return events;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	private Vector<String> getInvitedEvents(String username){
		Vector<String> events = new Vector<String>();
		try {
			PreparedStatement ps= con.prepareStatement("SELECT * FROM eventparticipants "
					+ "WHERE accepted=0 AND username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				events.add(rs.getString("eventID"));
			}
			return events;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
}
