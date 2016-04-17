package com.movieknight.server;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by Kevin on 4/16/2016.
 */
public class FirebaseDriver {
    private Firebase rootRef;

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
                login(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                //it didn't work
            }
        });
    }

    public void login(String email, String password) {
        rootRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                //Authenticated successfully with payload authData
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //Authentication failed with error firebaseError
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

    /*public void saveEventToDatabase(MovieEvent inMEvent) {
        Firebase eventRef = rootRef.child("events").child("");
    }*/

    public void saveUserToDatabase(String uid, Profile inProfile) {
        Firebase userRef = rootRef.child("users").child(uid);
        userRef.child("provider").setValue("password");
        userRef.child("username").setValue(inProfile.getUsername());
        userRef.child("profilepicture").setValue(inProfile.getProfilePicture());
        userRef.child("friends").setValue(inProfile.getFriends());
        userRef.child("towatch").setValue(inProfile.getToWatch());
        userRef.child("watched").setValue(inProfile.getWatched());
        userRef.child("liked").setValue(inProfile.getLiked());
        userRef.child("events").setValue(inProfile.getEvents());
        userRef.child("eventrequests").setValue(inProfile.getEventRequests());
    }
}
