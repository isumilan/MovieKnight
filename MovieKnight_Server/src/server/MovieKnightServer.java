package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MovieKnightServer extends JFrame {
	
	private static final long serialVersionUID = 9183816558021947333L;
	private static ServerListener serverListener;
	
	private ServerSocket ss;
	private Lock portLock;
	private Condition portCondition;
	private ServerLog log;
	
	{
		setTitle("Server");
		setSize(640,480);
		setMinimumSize(new Dimension(640,480));

		JPanel pane = new JPanel(new BorderLayout());
		JTextArea textArea = new JTextArea(20, 30);
		textArea.setEditable(false);
		log = new ServerLog(textArea);
		pane.add(log, BorderLayout.CENTER);
		
		setContentPane(pane);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		portLock = new ReentrantLock();
		portCondition = portLock.newCondition();
	}
	
	public MovieKnightServer() {
		ss = null;
		addActionAdapters();
		
		int port = 5000;
		try {
			ServerSocket tempss = new ServerSocket(port);
			portLock.lock();
			ss = tempss;
			serverListener = new ServerListener(ss, log);
			serverListener.start();
			log.write("Server started on Port: " + port);
			portCondition.signal();
			portLock.unlock();
		} catch (IOException e) {
			log.write("Error occurred starting server.");
			e.printStackTrace();
		}
	}
	
	private void addActionAdapters() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}
		
	public static void main(String [] args) {
		new MovieKnightServer();
	}
}
