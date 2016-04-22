package com.example.nathan.movieknight;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import com.example.nathan.movieknight.tmdb.TmdbConnector;
import com.example.nathan.movieknight.tmdb.TmdbService;
import com.example.nathan.movieknight.models.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Samuel Wang on 4/17/2016.
 */
public class MovieKnightAppli extends Application {

    private static MovieKnightAppli application;
    private static ConnectToServer cts;
    private static ClientListener clisten;
    private Context context;
    private boolean isGuest;
    private String userName;
    ArrayList<String> movieList_top;
    ArrayList<String> movieImages_top;
    ArrayList<Integer> movieID_top;

    //In theaters
    ArrayList<String> movieList_in;
    ArrayList<String> movieImages_in;
    ArrayList<Integer> movieID_in;

    ArrayList<String> movieList_upcoming;
    ArrayList<String> movieImages_upcoming;
    ArrayList<Integer> movieID_upcoming;

    TmdbConnector tmdbConnector;
    TmdbService tmdbService;
    Profile userProfile;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        cts = new ConnectToServer(this);
        cts.execute();
        try {
            clisten = cts.get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (ExecutionException ee) {
            ee.printStackTrace();
        }
        movieList_top = new ArrayList<String>();
        movieImages_top = new ArrayList<String>();
        movieID_top = new ArrayList<Integer>();

        movieList_in = new ArrayList<String>();
        movieImages_in = new ArrayList<String>();
        movieID_in = new ArrayList<Integer>();

        movieList_upcoming = new ArrayList<String>();
        movieImages_upcoming = new ArrayList<String>();
        movieID_upcoming = new ArrayList<Integer>();

        //getting the information for different class objects

    }

    public ClientListener getClisten() { return clisten; }

    public boolean isGuest() {
        return isGuest;
    }

    public void setIsGuest(boolean b) {
        isGuest = b;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String s) {
        userName = s;
    }
    public ArrayList<String> getMovieListTop(){
        return movieList_top;
    }
    public ArrayList<String> getMovieImagesTop(){
        return movieImages_top;
    }
    public ArrayList<Integer> getMovieIDTop(){
        return movieID_top;
    }
    public ArrayList<String> getMovieListIn(){
        return movieList_in;
    }
    public ArrayList<String> getMovieImagesIn(){
        return movieImages_in;
    }
    public ArrayList<Integer> getMovieIDIn(){
        return movieID_in;
    }
    public ArrayList<String> getMovieListUpcoming(){
        return movieList_upcoming;
    }
    public ArrayList<String> getMovieImagesUpcoming(){
        return movieImages_upcoming;
    }
    public ArrayList<Integer> getMovieIDUpcoming(){
        return movieID_upcoming;
    }

    //for use by MovieActivity
    public void setMovieService(TmdbService service) {tmdbService = service;}

    public TmdbService getMovieService() {
        return tmdbService;
    }
    public void setUserProfile(Profile prof){
        userProfile = prof;
    }
    public Profile getUserProfile(){
        return userProfile;
    }
    public void FriendRequestPopUp(){
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
        helpBuilder.setTitle("Friend Request");
        helpBuilder.setMessage("You received a friend request!");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
    public void EventInvitedPopUp(){
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context);
        helpBuilder.setTitle("Event Invite");
        helpBuilder.setMessage("You received an invitation to an event!");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
    public void setCurrentContext(Context context){
        this.context = context;
    }
}