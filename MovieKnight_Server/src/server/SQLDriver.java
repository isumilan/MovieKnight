package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Driver;

public class SQLDriver {
	
	/*private final static String selectName = "SELECT * FROM USER WHERE USERNAME=?";
	private final static String addUser = "INSERT INTO USER(USERNAME,PASSWORD) VALUES(?,?)";
	private final static String findPassword = "SELECT PASSWORD FROM USER WHERE USERNAME=?";
	private final static String findFiles = "SELECT FILENAME FROM FILES WHERE USERNAME=?";
	private final static String saveFile = "INSERT INTO FILES(FILENAME,CONTENT,USERNAME) VALUES(?,?,?)";
	private final static String updateFile = "UPDATE FILES SET CONTENT=? WHERE USERNAME=? AND FILENAME=?";
	private final static String selectFile = "SELECT CONTENT FROM FILES WHERE USERNAME=? AND FILENAME=?";*/
	private Connection con;
	
	public SQLDriver() {
		try {
			new Driver();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieknight_database?user=root&password=root");
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {return con;}
	
	public void stop() {
		try {
			con.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/*public boolean doesExist(String user) {
		try {
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, user);
			ResultSet result = ps.executeQuery();
			if (!result.next())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void register(String user, String pass) {
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1, user);
			ps.setString(2, pass);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean validate(String user, String pass) {
		try {
			PreparedStatement ps = con.prepareStatement(findPassword);
			ps.setString(1, user);
			ResultSet result = ps.executeQuery();
			if (result.next() && result.getString(1).equals(pass))
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Vector<String> getFiles(String user) {
		Vector<String> files = new Vector<String>();
		try {
			PreparedStatement ps = con.prepareStatement(findFiles);
			ps.setString(1, user);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				files.addElement(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return files;
	}
	
	public boolean upload(String user, String fn, Clob clob) {
		try {
			PreparedStatement ps = con.prepareStatement(saveFile);
			ps.setString(1, fn);
			ps.setClob(2, clob);
			ps.setString(3, user);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update(String user, String fn, Clob clob) {
		try {
			PreparedStatement ps = con.prepareStatement(updateFile);
			ps.setClob(1, clob);
			ps.setString(2, user);
			ps.setString(3, fn);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String open(String user, String fn) {
		try {
			PreparedStatement ps = con.prepareStatement(selectFile);
			ps.setString(1, user);
			ps.setString(2, fn);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				Clob data = result.getClob(1);
				Reader reader = data.getCharacterStream();
		        BufferedReader br = new BufferedReader(reader);

		        StringBuilder sb = new StringBuilder();
		        String line;
		        while(null != (line = br.readLine())) {
		            sb.append(line);
		        }
		        br.close();
		        return sb.toString();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}*/
}
