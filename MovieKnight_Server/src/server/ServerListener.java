package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServerListener extends Thread {

	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;
	private ServerLog mLog;
	
	public ServerListener(ServerSocket ss, ServerLog log) {
		this.ss = ss;
		mLog = log;
		sccVector = new Vector<ServerClientCommunicator>();
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		mLog.write("SCC removed");
		sccVector.remove(scc);
	}
	
	public ServerLog getLog() {
		return mLog;
	}
	
	public void run() {
		try {
			while(true) {
				Socket s = ss.accept();
				try {
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
					scc.start();
					sccVector.add(scc);
					mLog.write("SCC added");
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
