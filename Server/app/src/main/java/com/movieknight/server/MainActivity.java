package com.movieknight.server;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends Activity {
    private ServerSocket serverSocket = null;
    private Lock portLock;
    private Condition portCondition;
    public int SERVER_PORT = 5000;
    private static ServerListener serverListener;

    private TextView text;
    Handler updateConversationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        text = (TextView) findViewById(R.id.text2);
        updateConversationHandler = new Handler();

        portLock = new ReentrantLock();
        portCondition = portLock.newCondition();

        if (SERVER_PORT > 0 && SERVER_PORT < 65535) {
            try {
                portLock.lock();
                serverSocket = new ServerSocket(SERVER_PORT);
                serverListener = new ServerListener(serverSocket, text);
                serverListener.start();
                portCondition.signal();
                portLock.unlock();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            serverListener.writeToLog("Port out of range");
        }
    }

    protected void onStop() {
        super.onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
