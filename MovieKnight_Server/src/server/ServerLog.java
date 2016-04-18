package server;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerLog extends JScrollPane {
	
	private static final long serialVersionUID = -6244038805478874480L;
	private JTextArea mTextArea;
	
	ServerLog(JTextArea ta) {
		super(ta);
		mTextArea = ta;
	}
	
	public void write(String str) {
		mTextArea.append(str + '\n');
	}
}
