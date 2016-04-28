package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.example.nathan.movieknight.ServerClientDialogue;
import com.example.nathan.movieknight.models.MovieEvent;

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
			while(true){
				ServerClientDialogue scd = (ServerClientDialogue) ois.readObject();
				if (scd.getRequestType() == MovieConstants.ProfileRequest)
					sendObject(driver.getProfile((String) scd.getDialogueContent()));
				else if (scd.getRequestType() == MovieConstants.MovieEventRequest)
					sendObject(driver.getMovieEvent((String) scd.getDialogueContent()));
				else if (scd.getRequestType() == MovieConstants.LoginRequest) {
					String input = (String) scd.getDialogueContent();
					sendObject(driver.LoginRequest(input.split("\b")[0], input.split("\b")[1]));
				} else if (scd.getRequestType() == MovieConstants.RegisterRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.RegisterRequest(inArray[0], inArray[1], Integer.parseInt(inArray[2])));
				} else if (scd.getRequestType() == MovieConstants.MakeEventRequest || scd.getRequestType() == MovieConstants.EditMovieEventRequest) {
					sendObject(driver.MakeMovieEvent((MovieEvent) scd.getDialogueContent()));
				} else if (scd.getRequestType() == MovieConstants.FriendRequestRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.FriendRequest(inArray[0], inArray[1]));
				} else if (scd.getRequestType() == MovieConstants.FriendRequestReplyRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.AcceptFriend(inArray[0], inArray[1], Boolean.parseBoolean(inArray[2])));
				} else if (scd.getRequestType() == MovieConstants.EventInviteRequest) {
					//scoped out for now
				} else if (scd.getRequestType() == MovieConstants.EventReplyRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.EventReply(inArray[0], inArray[1], Boolean.parseBoolean(inArray[2])));
				} else if (scd.getRequestType() == MovieConstants.AddToToWatchListRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.AddToList("towatch", Integer.parseInt(inArray[0]), inArray[1]));
				} else if (scd.getRequestType() == MovieConstants.AddToLikedListRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.AddToList("liked", Integer.parseInt(inArray[0]), inArray[1]));
				} else if (scd.getRequestType() == MovieConstants.AddToWatchedListRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.AddToList("watched", Integer.parseInt(inArray[0]), inArray[1]));
				} else if (scd.getRequestType() == MovieConstants.UpdatePersonalDescriptionRequest) {
					String input = (String) scd.getDialogueContent();
					String[] inArray = input.split("\b");
					sendObject(driver.EditDescription(inArray[0], inArray[1]));
				} else if (scd.getRequestType() == MovieConstants.ListAllUsersRequest){
					Set<String> usernames = driver.ListAllUsers();
					Iterator<String> it = usernames.iterator();
					Vector<String> list = new Vector<String>();
					while(it.hasNext()){
						list.add(it.next());
					}
					sendObject(list);
				} else if (scd.getRequestType() == MovieConstants.HasSeenRequestsRequest) {
					String input = (String) scd.getDialogueContent();
					sendObject(driver.HasSeenRequests(input));
				} else if (scd.getRequestType() == MovieConstants.HasSeenInvitesRequest) {
					String input = (String) scd.getDialogueContent();
					sendObject(driver.HasSeenInvites(input));
				}
			}
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			serverListener.removeServerClientCommunicator(this);
			try {
				socket.close();
			} catch (IOException ioe1) {
				ioe1.printStackTrace();
			}
		} catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } finally{
        	if(driver != null){
        		driver.stop();
        	}
        }
	}
}
