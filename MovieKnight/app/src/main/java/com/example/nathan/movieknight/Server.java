package com.example.nathan.movieknight;

import com.firebase.client.Firebase;

/**
 * Created by Kevin on 4/13/2016.
 */

public class Server {
    private Firebase rootRef;

    Server() {
        rootRef = new Firebase("http://fiery-torch-6805.firebaseIO.com");
    }
}
