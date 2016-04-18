package com.example.nathan.movieknight;

import android.app.Application;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Samuel Wang on 4/17/2016.
 */
public class MovieKnightAppli extends Application {
    private static MovieKnightAppli application;
    private static ClientListener clisten;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        try {
            clisten = new ClientListener(new Socket("10.0.2.2", 5000));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}