package server;

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
		SQLDriver driver = new SQLDriver();
		driver.connect();
		try {
            String line = (String) ois.readObject();
            while (line != null) {
            	serverListener.getLog().write("Client: " + line);
            }
		} catch (IOException ioe) {
			serverListener.removeServerClientCommunicator(this);
			try {
				socket.close();
			} catch (IOException ioe1) {
				ioe1.printStackTrace();
			}
		} catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
		driver.stop();
	}
}
