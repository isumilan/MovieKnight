package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
		try {
            String line = (String) ois.readObject();
            while (line != null) {
            	serverListener.getLog().write("Client: " + line);
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
	}
	
	/*private Movie getMovie(String title){
    	//get movie from firebase using title
    }
    private Profile getProfile(String username){
    	//get user profile from firebase using username
    }
    private MovieEvent getMovieEvent(String eventID){
    	//get movie event from firebase using eventID
    }
    private boolean LoginRequest(String username, String password){
    	//request login using username and password
    }
    private boolean RegisterRequest(String email, String username
    		, String password, int zipcode){
    	//request to create new user
    }
    private boolean MakeMovieEvent(MovieEvent){
    	//create such event
    }
    private boolean FriendRequest(String username){
    	//send friend request to user with such username
    }
    private boolean AcceptFriend(String username){
    	//accept the friend request from the user with such username
    }
    private boolean EventInvite(String eventID, String username){
    	//invite the user to the event
    }
    private boolean EventReply(String eventID, String username){
    	//the reply of the user to the event
    }*/
}
