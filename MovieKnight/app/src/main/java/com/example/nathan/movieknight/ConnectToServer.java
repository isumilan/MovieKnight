package com.example.nathan.movieknight;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Samuel Wang on 4/17/2016.
 */
public class ConnectToServer extends AsyncTask<Void, Void, ClientListener> {
    private ClientListener c;

    protected ClientListener doInBackground(Void... voids) {
        try {
            c = new ClientListener(new Socket("10.0.2.2", 5000));
            return c;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


}
