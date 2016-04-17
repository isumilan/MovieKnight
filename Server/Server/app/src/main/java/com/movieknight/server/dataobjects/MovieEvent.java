package com.movieknight.server.dataobjects;
import java.util.Comparator;
import java.util.Vector;

public class MovieEvent {
	
	public static class MovieComparator implements Comparator<MovieEvent>{
		public int compare(MovieEvent arg0, MovieEvent arg1) {
			return arg0.getGoingToWatch().getTitle().compareTo(arg1.getGoingToWatch().getTitle());
		}		
	}
	public static class MovieTimeComparator implements Comparator<MovieEvent>{
		public int compare(MovieEvent arg0, MovieEvent arg1) {
			return arg0.getMovieTime().compareTo(arg1.getMovieTime());
		}		
	}
	
	private Movie goingToWatch;
	private Vector<Profile> participants, invited;
	private String description, movieTime, theater;
	private MovieComparator mc = new MovieComparator();
	private MovieTimeComparator mtc = new MovieTimeComparator();
	
	
	//Getters
	public Movie getGoingToWatch() {
		return goingToWatch;
	}
	public Vector<Profile> getParticipants() {
		return participants;
	}
	public Vector<Profile> getInvited() {
		return invited;
	}
	public String getMovieTime() {
		return movieTime;
	}
	public String getTheater() {
		return theater;
	}
	public String getDescription() {
		return description;
	}
	public MovieComparator getMovieComparator() {
		return mc;
	}
	public MovieTimeComparator getMovieTimeCompartor() {
		return mtc;
	}
	
	//Mutators
	public void setGoingToWatch(Movie goingToWatch) {
		this.goingToWatch = goingToWatch;
	}
	public void setParticipants(Vector<Profile> participants) {
		this.participants = participants;
	}
	public void setInvited(Vector<Profile> invited) {
		this.invited = invited;
	}
	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}
	public void setTheater(String theater) {
		this.theater = theater;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void addParticipant(Profile newParticipant){
		participants.add(newParticipant);
	}
	public void addInvited(Profile newInvitee){
		invited.add(newInvitee);
	}
	public void removeParticipant(Profile quitter){
		participants.remove(quitter);
	}
	public void removeInvited(Profile tfti){
		invited.remove(tfti);
	}
}
