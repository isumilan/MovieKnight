package com.example.nathan.movieknight;

import android.app.Application;

/**
 * Created by Samuel Wang on 4/17/2016.
 */
public class MovieKnightAppli extends Application {
    private static MovieKnightAppli application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
