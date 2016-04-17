package com.example.nathan.movieknight.models;
import android.graphics.Movie;

import java.util.Comparator;
import java.util.Vector;

public class Profile {
	
	public static class UsernameComparator implements Comparator<Profile>{
		public int compare(Profile arg0, Profile arg1) {
			return arg0.getUsername().compareTo(arg1.getUsername());
		}		
	}
	
	

	
	private String username, profilePicture, description;
	private Vector<Profile> friends, friendRequests;
	private Vector<Integer> toWatch, watched, liked;
	private Vector<MovieEvent> events, eventRequests;
	private int zipcode;
	private UsernameComparator ucomp = new UsernameComparator();
	
	
	//constructor for generic com.example.nathan.movieknight.models.Profile
	public Profile(){
		username = "*Name unavailable";
		profilePicture = "NoImageAvailable.png"; //This needs to be changed
		description = "no description";
		friends = new Vector<Profile>();
		friendRequests = new Vector<Profile>();
		toWatch = new Vector<Integer>();
		watched = new Vector<Integer>();
		liked = new Vector<Integer>();
		events = new Vector<MovieEvent>();
		eventRequests = new Vector<MovieEvent>();
		zipcode = 0;
	}
	
	public Profile(String name, String pic, int zip){
		username = name;
		profilePicture = pic;
		description = "no description";
		friends = new Vector<Profile>();
		friendRequests = new Vector<Profile>();
		toWatch = new Vector<Integer>();
		watched = new Vector<Integer>();
		liked = new Vector<Integer>();
		events = new Vector<MovieEvent>();
		eventRequests = new Vector<MovieEvent>();
		zipcode = zip;
	}
	//Getters
	public String getUsername() {
		return username;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public String getDescription() {
		return description;
	}
	public Vector<Profile> getFriends() {
		return friends;
	}
	public Vector<Profile> getFriendRequests() {
		return friendRequests;
	}
	public Vector<Integer> getToWatch() {
		return toWatch;
	}
	public Vector<Integer> getWatched() {
		return watched;
	}
	public Vector<Integer> getLiked() {
		return liked;
	}
	public Vector<MovieEvent> getEvents() {
		return events;
	}
	public Vector<MovieEvent> getEventRequests() {
		return eventRequests;
	}
	public int getZipcode() {
		return zipcode;
	}
	public UsernameComparator getUsernameComparator() {
		return ucomp;
	}
	
	//Mutators
	public void setUsername(String username) {
		this.username = username;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setFriends(Vector<Profile> friends) {
		this.friends = friends;
	}
	public void setFriendRequests(Vector<Profile> friendRequests) {
		this.friendRequests = friendRequests;
	}
	public void setToWatch(Vector<Integer> toWatch) {
		this.toWatch = toWatch;
	}
	public void setWatched(Vector<Integer> watched) {
		this.watched = watched;
	}
	public void setLiked(Vector<Integer> liked) {
		this.liked = liked;
	}
	public void setEvents(Vector<MovieEvent> events) {
		this.events = events;
	}
	public void setEventRequests(Vector<MovieEvent> eventRequests) {
		this.eventRequests = eventRequests;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	
	
	
}
