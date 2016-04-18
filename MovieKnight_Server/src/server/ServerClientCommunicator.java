package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import models.MovieEvent;

public class ServerClientCommunicator extends Thread {

	private Socket socket;
	private ServerListener serverListener;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
		this.socket = socket;
		this.serverListener = serverListener;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	
	public void sendObject(Object obj) {
		try {
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		SQLDriver driver = new SQLDriver(serverListener.getLog());
		driver.connect();
		try {
			ServerClientDialogue scd = (ServerClientDialogue) ois.readObject();
			if (scd.getRequestType() == MovieConstants.ProfileRequest)
				sendObject(driver.getProfile((String) scd.getDialogueContent()));
			else if (scd.getRequestType() == MovieConstants.MovieEventRequest)
				sendObject(driver.getMovieEvent((String) scd.getDialogueContent()));
			else if (scd.getRequestType() == MovieConstants.LoginRequest) {
				String input = (String) scd.getDialogueContent();
				sendObject(driver.LoginRequest(input.split("|")[0], input.split("|")[1]));
			} else if (scd.getRequestType() == MovieConstants.RegisterRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.RegisterRequest(inArray[0], inArray[1], Integer.parseInt(inArray[2])));
			} else if (scd.getRequestType() == MovieConstants.MakeEventRequest || scd.getRequestType() == MovieConstants.EditMovieEventRequest) {
				sendObject(driver.MakeMovieEvent((MovieEvent) scd.getDialogueContent()));
			} else if (scd.getRequestType() == MovieConstants.FriendRequestRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.FriendRequest(inArray[0], inArray[1]));
			} else if (scd.getRequestType() == MovieConstants.FriendRequestReplyRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.AcceptFriend(inArray[0], inArray[1], Boolean.parseBoolean(inArray[2])));
			} else if (scd.getRequestType() == MovieConstants.EventInviteRequest) {
				//scoped out for now
			} else if (scd.getRequestType() == MovieConstants.EventReplyRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.EventReply(inArray[0], inArray[1], Boolean.parseBoolean(inArray[2])));
			} else if (scd.getRequestType() == MovieConstants.AddToToWatchListRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.AddToList("towatch", Integer.parseInt(inArray[0]), inArray[1]));
			} else if (scd.getRequestType() == MovieConstants.AddToLikedListRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.AddToList("liked", Integer.parseInt(inArray[0]), inArray[1]));
			} else if (scd.getRequestType() == MovieConstants.AddToWatchedListRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.AddToList("watched", Integer.parseInt(inArray[0]), inArray[1]));
			} else if (scd.getRequestType() == MovieConstants.UpdatePersonalDescriptionRequest) {
				String input = (String) scd.getDialogueContent();
				String[] inArray = input.split("|");
				sendObject(driver.EditDescription(inArray[0], inArray[1]));
			}
		} catch (IOException ioe) {
			serverListener.removeServerClientCommunicator(this);
			try {
				socket.close();
			} catch (IOException ioe1) {
				ioe1.printStackTrace();
			}
		} catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
		driver.stop();
	}
}
