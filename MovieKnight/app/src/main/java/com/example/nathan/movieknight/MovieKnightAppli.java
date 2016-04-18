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

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        cts = new ConnectToServer();
        cts.execute();
        clisten = cts.getClientListener();
    }
}