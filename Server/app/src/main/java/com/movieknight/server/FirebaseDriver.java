package com.movieknight.server;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.movieknight.server.custom_objects.Movie;
import com.movieknight.server.custom_objects.MovieEvent;
import com.movieknight.server.custom_objects.Profile;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kevin on 4/16/2016.
 */
public class FirebaseDriver {
    private Firebase rootRef;
    private ServerListener mListener;

    {
        rootRef = new Firebase("http://fiery-torch-6805.firebaseIO.com");
        rootRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    //user is logged in
                } else {
                    //user is not logged in
                }
            }
        });
    }

    public FirebaseDriver(ServerListener serverListener) {
        mListener = serverListener;
    }

    public boolean isLoggedIn() {
        if (rootRef.getAuth() != null) return true;
        else return false;
    }

    public void register(final String email, final String password) {
        rootRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Profile user = new Profile();
                saveUserToDatabase((String) result.get("uid"), user);
                mListener.writeToLog(email + " registered successfully");
                login(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                mListener.writeToLog(email + " failed to register");
            }
        });
    }

    public void login(final String email, final String password) {
        rootRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mListener.writeToLog(email + " logged in successfully");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mListener.writeToLog(email + " failed to log in");
            }
        });
    }

    public void logout() { rootRef.unauth(); }

    public void saveMovieToDatabase(Movie inMovie) {
        Firebase movieRef = rootRef.child("movies").child(inMovie.getTitle());
        movieRef.child("title").setValue(inMovie.getTitle());
        movieRef.child("poster").setValue(inMovie.getPoster());
        movieRef.child("synopsis").setValue(inMovie.getSynopsis());
        movieRef.child("ratescore").setValue(inMovie.getRateScore());
        movieRef.child("rated").setValue(inMovie.getRated());
        movieRef.child("rating").setValue(inMovie.getRating());
        movieRef.child("genre").setValue(inMovie.getGenre());
        movieRef.child("casts").setValue(inMovie.getCasts());
        movieRef.child("directors").setValue(inMovie.getDirectors());
        movieRef.child("trailers").setValue(inMovie.getTrailers());
    }

    public void saveEventToDatabase(MovieEvent inMEvent) {
        Firebase eventRef = rootRef.child("events").child(inMEvent.getEventID());
        eventRef.child("owner").setValue(inMEvent.getOwner().getUsername());
        eventRef.child("goingtowatch").setValue(inMEvent.getGoingToWatch().getTitle());

        ArrayList<String> participants = new ArrayList<String>();
        for (Profile p : inMEvent.getParticipants()) {
            participants.add(p.getUsername());
        }
        eventRef.child("participants").setValue(participants);

        ArrayList<String> invited = new ArrayList<String>();
        for (Profile p : inMEvent.getInvited()) {
            invited.add(p.getUsername());
        }
        eventRef.child("participants").setValue(invited);

        eventRef.child("movietime").setValue(inMEvent.getMovieTime());
        eventRef.child("theater").setValue(inMEvent.getTheater());
        eventRef.child("description").setValue(inMEvent.getDescription());
    }

    public void saveUserToDatabase(String uid, Profile inProfile) {
        Firebase userRef = rootRef.child("users").child(uid);
        userRef.child("provider").setValue("password");
        userRef.child("username").setValue(inProfile.getUsername());
        userRef.child("profilepicture").setValue(inProfile.getProfilePicture());

        ArrayList<String> friends = new ArrayList<String>();
        for (Profile friend : inProfile.getFriends()) {
            friends.add(friend.getUsername());
        }
        userRef.child("friends").setValue(friends);

        ArrayList<String> friendrequests = new ArrayList<String>();
        for (Profile friendrequest : inProfile.getFriendRequests()) {
            friendrequests.add(friendrequest.getUsername());
        }
        userRef.child("friendrequests").setValue(friendrequests);

        ArrayList<String> towatch = new ArrayList<String>();
        for (Movie m : inProfile.getToWatch()) {
            towatch.add(m.getTitle());
        }
        userRef.child("towatch").setValue(towatch);

        ArrayList<String> watched = new ArrayList<String>();
        for (Movie m : inProfile.getWatched()) {
            watched.add(m.getTitle());
        }
        userRef.child("watched").setValue(watched);

        ArrayList<String> liked = new ArrayList<String>();
        for (Movie m : inProfile.getLiked()) {
            liked.add(m.getTitle());
        }
        userRef.child("liked").setValue(liked);

        ArrayList<String> events = new ArrayList<String>();
        for (MovieEvent e : inProfile.getEvents()) {
            events.add(e.getEventID());
        }
        userRef.child("events").setValue(events);

        ArrayList<String> eventrequests = new ArrayList<String>();
        for (MovieEvent e : inProfile.getEventRequests()) {
            eventrequests.add(e.getEventID());
        }
        userRef.child("eventrequests").setValue(eventrequests);
    }
}
