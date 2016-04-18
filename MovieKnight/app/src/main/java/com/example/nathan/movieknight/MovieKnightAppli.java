package com.example.nathan.movieknight;

import android.app.Application;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Samuel Wang on 4/17/2016.
 */
public class MovieKnightAppli extends Application {
    private static MovieKnightAppli application;
    private static ConnectToServer cts;
    private static ClientListener clisten;

    private boolean isGuest;
    private String userName;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        cts = new ConnectToServer();
        cts.execute();
        clisten = cts.getClientListener();
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
}