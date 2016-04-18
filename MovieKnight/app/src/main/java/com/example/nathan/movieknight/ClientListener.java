package com.example.nathan.movieknight;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Kevin on 4/16/2016.
 */

public class ClientListener extends Thread {

    private Socket mSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientListener(Socket inSocket) {
        mSocket = inSocket;
        boolean socketReady = initializeVariables();
        if (socketReady) {
            start();
        }
    }

    private boolean initializeVariables() {
        try {
            ois = new ObjectInputStream(mSocket.getInputStream());
            oos = new ObjectOutputStream(mSocket.getOutputStream());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendObject(Object obj) {
        try {
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while(true) {
                //if server sends an object
                Object obj = ois.readObject();
                //do whatever the fuck with it
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
    
   /* public ServerClientDialogue MovieRequest(String title){
    	return new ServerClientDialogue(MovieConstants.MovieRequest, title);
    }*/
    public ServerClientDialogue ProfileRequest(String name){
    	return new ServerClientDialogue(MovieConstants.ProfileRequest, name);
    }
    public ServerClientDialogue MovieEventRequest(String eventID){
    	return new ServerClientDialogue(MovieConstants.MovieEventRequest, eventID);
    }
    public ServerClientDialogue LoginRequest(String name, String password){
    	return new ServerClientDialogue(MovieConstants.LoginRequest
    			, name + "|" + password);
    }
    public ServerClientDialogue RegisterRequest(String name, String password, int zip){
    	return new ServerClientDialogue(MovieConstants.RegisterRequest
    			, name + "|" + password + "|" + zip);
    }
    public ServerClientDialogue MakeEventRequest(String owner, int goingToWatch
    		, boolean public_private, String EventTitle, String time
    		, String location, Vector<String> invitations){
    	MovieEvent me = new MovieEvent(owner, goingToWatch);
    	me.setPublic_private(public_private);
    	me.setDescription(EventTitle);
    	me.setMovieTime(time);
    	me.setTheater(location);
    	me.setInvited(invitations);
    	return new ServerClientDialogue(MovieConstants.MakeEventRequest, me);
    }
    public ServerClientDialogue FriendRequestRequest(String subject, String object){
    	return new ServerClientDialogue(MovieConstants.FriendRequestRequest
    			, subject + "|" + object);
    	//subject sends friend request to object
    }
    public ServerClientDialogue FriendRequestReplyRequest(String subject, String object, boolean reply){
    	return new ServerClientDialogue(MovieConstants.FriendRequestReplyRequest
    			, subject+"|"+object+"|"+reply);
    	//subject replies to object's friend request with reply(true = yes)
    }
    /*public ServerClientDialogue EventInviteRequest(String eventID, String name){
    	return new ServerClientDialogue(MovieConstants.MovieRequest, title);
    }*/
    public ServerClientDialogue EventReplyRequest(String eventID, String name, boolean reply){
    	return new ServerClientDialogue(MovieConstants.EventReplyRequest
    			, eventID+"|"+name+"|"+reply);
    }
    public ServerClientDialogue AddToToWatchListRequest(int movieID, String name){
    	return new ServerClientDialogue(MovieConstants.AddToToWatchListRequest
    			, movieID+"|"+name);
    }
    public ServerClientDialogue AddToLikedListRequest(int movieID, String name){
    	return new ServerClientDialogue(MovieConstants.AddToLikedListRequest
    			, movieID+"|"+name);
    }
    public ServerClientDialogue AddToWatchedListRequest(int movieID, String name){
    	return new ServerClientDialogue(MovieConstants.AddToWatchedListRequest
    			, movieID+"|"+name);
    }
    public ServerClientDialogue UpdatePersonalDescriptionRequest(String description, String name){
    	return new ServerClientDialogue(MovieConstants.UpdatePersonalDescriptionRequest
    			, description+"|"+name);
    }
    public ServerClientDialogue EditMovieEventRequest(MovieEvent me){
    	return new ServerClientDialogue(MovieConstants.EditMovieEventRequest
    			, me);
    }
}
