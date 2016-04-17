package com.movieknight.server;

/**
 * Created by Kevin on 4/16/2016.
 */
import android.widget.TextView;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerListener extends Thread {

    private ServerSocket ss;
    private TextView log;
    private ArrayList<ServerClientCommunicator> sccVector;

    public ServerListener(ServerSocket ss, TextView inTextView) {
        this.ss = ss;
        this.log = inTextView;
        sccVector = new ArrayList<ServerClientCommunicator>();
    }

    public void removeServerClientCommunicator(ServerClientCommunicator scc) {
        sccVector.remove(scc);
    }

    public TextView getLog() { return log; }

    public void run() {
        try {
            while(true) {
                Socket s = ss.accept();
                try {
                    ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
                    scc.start();
                    sccVector.add(scc);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch(BindException be) {
            be.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}