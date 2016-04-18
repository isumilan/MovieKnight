package com.example.nathan.movieknight;

import android.app.Application;

<<<<<<< HEAD
=======
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

>>>>>>> 85513ac2f18b350ab9b60e438a53908dae908f70
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
        try {
            clisten = cts.get();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (ExecutionException ee) {
            ee.printStackTrace();
        }
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