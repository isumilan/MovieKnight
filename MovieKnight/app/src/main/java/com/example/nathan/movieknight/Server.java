package com.example.nathan.movieknight;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.Map;
import java.util.Vector;

/**
 * Created by Kevin on 4/13/2016.
 */

public class Server {
    private Firebase rootRef;

    Server() {
        rootRef = new Firebase("http://fiery-torch-6805.firebaseIO.com");
        rootRef.addAuthStateListener(new Firebase.AuthStateListener() {
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    //user is logged in
                } else {
                    //user is not logged in
                }
            }
        });
    }

    public void saveMovieToDatabase(Movie inMovie) {
        Firebase movieRef = rootRef.child("movies").child(inMovie.getTitle());
        movieRef.setValue(inMovie);
    }

    public Vector<Movie> getMoviesFromDatabase(String sortByParam) {
        Firebase movieRef = rootRef.child("movies");
        Query queryRef = movieRef.orderByChild(sortByParam);
        final Vector<Movie> sortedMovies = new Vector<Movie>();
        class MovieListener implements ChildEventListener {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sortedMovies.add(dataSnapshot.getValue(Movie.class));
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(FirebaseError firebaseError) {}
        }
        queryRef.addChildEventListener(new MovieListener());
        return sortedMovies;
    }

    public boolean isLoggedIn() {
        if (rootRef.getAuth() != null) return true;
        else return false;
    }

    public void register(String email, String password) {
        rootRef.createUser(email, PasswordEncryptor.encrypt(password), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                //it worked
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                //it didn't work
            }
        });
    }

    public void login(String email, String password) {
        rootRef.authWithPassword(email, PasswordEncryptor.encrypt(password), new Firebase.AuthResultHandler() {
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

    public void logout() {
        rootRef.unauth();
    }
}
