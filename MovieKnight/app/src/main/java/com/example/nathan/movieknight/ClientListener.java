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
}
