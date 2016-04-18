package com.example.nathan.movieknight;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Samuel Wang on 4/17/2016.
 */
public class ConnectToServer extends AsyncTask<Void, Void, ClientListener> {
    protected ClientListener doInBackground(Void... voids) {
        try {
            return new ClientListener(new Socket("10.0.2.2", 5000));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
