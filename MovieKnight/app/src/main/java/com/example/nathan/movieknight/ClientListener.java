package com.example.nathan.movieknight;

import com.example.nathan.movieknight.models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    public Profile ProfileRequest(String username)throws IOException, ClassNotFoundException{
    	oos.writeObject(ProfileRequestDialogue(username));
    	return (Profile)ois.readObject();
    }
    public MovieEvent MovieEventRequest(String eventID)throws IOException, ClassNotFoundException{
    	oos.writeObject(MovieEventRequestDialogue(eventID));
    	return (MovieEvent)ois.readObject();
    }
    public boolean LoginRequest(String username, String password)throws IOException, ClassNotFoundException{
    	oos.writeObject(LoginRequestDialogue(username, password));
    	return (boolean)ois.readObject();
    }
    public boolean RegisterRequest(String username, String password, int zip)throws IOException, ClassNotFoundException{
    	oos.writeObject(RegisterRequestDialogue(username, password, zip));
    	return (boolean)ois.readObject();
    }
    public boolean MakeEventRequest(String owner, int goingToWatch
    		, boolean public_private, String EventTitle, String time
    		, String location, Vector<String> invitations)throws IOException, ClassNotFoundException{
    	oos.writeObject(MakeEventRequestDialogue(owner, goingToWatch
    			, public_private, EventTitle, time, location, invitations));
    	return (boolean)ois.readObject();
    }
    public boolean FriendRequestRequest(String subject, String object)throws IOException, ClassNotFoundException{
    	oos.writeObject(FriendRequestRequestDialogue(subject, object));
    	return (boolean)ois.readObject();
    }
    public boolean FriendRequestReplyRequest(String subject, String object, boolean reply)throws IOException, ClassNotFoundException{
    	oos.writeObject(FriendRequestReplyRequestDialogue(subject, object, reply));
    	return (boolean)ois.readObject();
    }
    public boolean EventReplyRequest(String eventID, String username, boolean reply)throws IOException, ClassNotFoundException{
    	oos.writeObject(EventReplyRequestDialogue(eventID, username, reply));
    	return (boolean)ois.readObject();
    }
    public boolean AddToToWatchListRequest(int movieID, String username)throws IOException, ClassNotFoundException{
    	oos.writeObject(AddToToWatchListRequestDialogue(movieID, username));
    	return (boolean)ois.readObject();
    }
    public boolean AddToLikedListRequest(int movieID, String username)throws IOException, ClassNotFoundException{
    	oos.writeObject(AddToLikedListRequestDialogue(movieID, username));
    	return (boolean)ois.readObject();
    }
    public boolean AddToWatchListRequest(int movieID, String username)throws IOException, ClassNotFoundException{
    	oos.writeObject(AddToWatchedListRequestDialogue(movieID, username));
    	return (boolean)ois.readObject();
    }
    public boolean UpdatePersonalDescriptionRequest(String description, String username)throws IOException, ClassNotFoundException{
    	oos.writeObject(UpdatePersonalDescriptionRequestDialogue(description, username));
    	return (boolean)ois.readObject();
    }
    public boolean  EditMovieEventRequest(MovieEvent me)throws IOException, ClassNotFoundException{
    	oos.writeObject(EditMovieEventRequestDialogue(me));
    	return (boolean)ois.readObject();
    }
    
    
   /* public ServerClientDialogue MovieRequest(String title){
    	return new ServerClientDialogue(MovieConstants.MovieRequest, title);
    }*/
    private ServerClientDialogue ProfileRequestDialogue(String name){
    	return new ServerClientDialogue(MovieConstants.ProfileRequest, name);
    }
    private ServerClientDialogue MovieEventRequestDialogue(String eventID){
    	return new ServerClientDialogue(MovieConstants.MovieEventRequest, eventID);
    }
    private ServerClientDialogue LoginRequestDialogue(String name, String password){
    	return new ServerClientDialogue(MovieConstants.LoginRequest
    			, name + "|" + password);
    }
    private ServerClientDialogue RegisterRequestDialogue(String name, String password, int zip){
    	return new ServerClientDialogue(MovieConstants.RegisterRequest
    			, name + "|" + password + "|" + zip);
    }
    private ServerClientDialogue MakeEventRequestDialogue(String owner, int goingToWatch
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
    private ServerClientDialogue FriendRequestRequestDialogue(String subject, String object){
    	return new ServerClientDialogue(MovieConstants.FriendRequestRequest
    			, subject + "|" + object);
    	//subject sends friend request to object
    }
    private ServerClientDialogue FriendRequestReplyRequestDialogue(String subject, String object, boolean reply){
    	return new ServerClientDialogue(MovieConstants.FriendRequestReplyRequest
    			, subject+"|"+object+"|"+reply);
    	//subject replies to object's friend request with reply(true = yes)
    }
    /*public ServerClientDialogue EventInviteRequest(String eventID, String name){
    	return new ServerClientDialogue(MovieConstants.MovieRequest, title);
    }*/
    private ServerClientDialogue EventReplyRequestDialogue(String eventID, String name, boolean reply){
    	return new ServerClientDialogue(MovieConstants.EventReplyRequest
    			, eventID+"|"+name+"|"+reply);
    }
    private ServerClientDialogue AddToToWatchListRequestDialogue(int movieID, String name){
    	return new ServerClientDialogue(MovieConstants.AddToToWatchListRequest
    			, movieID+"|"+name);
    }
    private ServerClientDialogue AddToLikedListRequestDialogue(int movieID, String name){
    	return new ServerClientDialogue(MovieConstants.AddToLikedListRequest
    			, movieID+"|"+name);
    }
    private ServerClientDialogue AddToWatchedListRequestDialogue(int movieID, String name){
    	return new ServerClientDialogue(MovieConstants.AddToWatchedListRequest
    			, movieID+"|"+name);
    }
    private ServerClientDialogue UpdatePersonalDescriptionRequestDialogue(String description, String name){
    	return new ServerClientDialogue(MovieConstants.UpdatePersonalDescriptionRequest
    			, description+"|"+name);
    }
    private ServerClientDialogue EditMovieEventRequestDialogue(MovieEvent me){
    	return new ServerClientDialogue(MovieConstants.EditMovieEventRequest
    			, me);
    }
}
