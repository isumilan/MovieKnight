package com.example.nathan.movieknight.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chaitanyap7 on 4/15/16.
 */
public class MovieResults {

    @SerializedName("page")
    private String moviePage;


    @SerializedName("results")
    private List<Movie> movieResults;

    public List<Movie> getMovies() {
        return movieResults;
    }
}
