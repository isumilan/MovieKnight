package com.example.nathan.movieknight.models;
import java.util.Comparator;
import java.util.Vector;



public class Movie {
	
	public static class TitleComparator implements Comparator<Movie>{
		public int compare(Movie arg0, Movie arg1) {
			return arg0.getTitle().compareTo(arg1.getTitle());
		}		
	}

	public static class RatingComparator implements Comparator<Movie>{
		public int compare(Movie arg0, Movie arg1) {
			return (int)(arg0.getRating() - arg1.getRating());
		}		
	}
	
	private String title, poster, synopsis;
	private int rateScore, rated; //rateScore is total, rated is the amount of ppl
	private Double rating; //the average
	private Vector<String> genre, casts, directors, trailers;
	private TitleComparator titlecomp = new TitleComparator();
	private RatingComparator ratecomp = new RatingComparator();
	
	

	//Constructor for movie with no info
	public Movie(){
		title = "*Untitled";
		poster = "NoImageAvailable.png"; //This needs to be changed
		synopsis = "*No synopsis available";
		rateScore = 0;
		rated = 0;
		rating = 0.0;
		genre = new Vector<String>();
		casts = new Vector<String>();
		directors = new Vector<String>();
		trailers = new Vector<String>();
	}
	
	//Constructor with everything
	public Movie(String title, String poster, String synopsis, Vector<String> genre
			, Vector<String> casts, Vector<String> directors, Vector<String> trailers){
		this.title = title;
		this.poster = poster; //This needs to be changed
		this.synopsis = synopsis;
		//here I'm assuming rating always starts with 0
		//there shouldn't be rating for a newly created movie(?
		rateScore = 0;
		rated = 0;
		rating = 0.0;
		this.genre = genre;
		this.casts = casts;
		this.directors = directors;
		this.trailers = trailers;
	}
	
	//Getters
	public String getTitle() {
		return title;
	}

	public String getPoster() {
		return poster;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public int getRateScore() {
		return rateScore;
	}

	public int getRated() {
		return rated;
	}

	public Double getRating() {
		//just to be sure it is the most updated
		rating = ((double)rateScore)/((double)rated);
		return rating;
	}

	public Vector<String> getGenre() {
		return genre;
	}

	public Vector<String> getCasts() {
		return casts;
	}

	public Vector<String> getDirectors() {
		return directors;
	}

	public Vector<String> getTrailers() {
		return trailers;
	}
	
	public TitleComparator getTitleComparator() {
		return titlecomp;
	}

	public RatingComparator getRatingComparator() {
		return ratecomp;
	}
	
	//Mutators
	public void setTitle(String title) {
		this.title = title;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setRateScore(int rateScore) {
		this.rateScore = rateScore;
	}

	public void setRated(int rated) {
		this.rated = rated;
	}
	
	public void rate(int score){
		rateScore += score;
		rated ++;
		rating = ((double)rateScore)/((double)rated);
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public void setGenre(Vector<String> genre) {
		this.genre = genre;
	}

	public void setCasts(Vector<String> casts) {
		this.casts = casts;
	}

	public void setDirectors(Vector<String> directors) {
		this.directors = directors;
	}

	public void setTrailers(Vector<String> trailers) {
		this.trailers = trailers;
	}
	
	public void addGenre(String element){
		this.genre.add(element);
	}
	
	public void addCast(String element){
		this.casts.add(element);
	}
	
	public void addDirector(String element){
		this.directors.add(element);
	}
	
	public void addTrailer(String element){
		this.trailers.add(element);
	}
}
