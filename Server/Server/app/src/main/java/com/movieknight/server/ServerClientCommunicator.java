package com.movieknight.server;

/**
 * Created by Kevin on 4/16/2016.
 */
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientCommunicator extends Thread {

    private Socket socket;
    private ServerListener serverListener;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
        this.socket = socket;
        this.serverListener = serverListener;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
    }

    public void sendObject(Object obj) {
        try {
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void run() {
		try {
            String line = (String) ois.readObject();
            while (line != null) {
                TextView text = serverListener.getLog();
                text.setText(text.getText().toString() + "Client Says: " + line + "\n");
            }
		} catch (IOException ioe) {
			serverListener.removeServerClientCommunicator(this);
			// this means that the socket is closed since no more lines are being received
			try {
				socket.close();
			} catch (IOException ioe1) {
				ioe1.printStackTrace();
			}
		} catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
}
