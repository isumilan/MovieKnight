package com.example.nathan.movieknight.tmdb;


import android.util.Log;

import com.example.nathan.movieknight.models.MovieBox;
import com.example.nathan.movieknight.models.MovieInfo;
import com.example.nathan.movieknight.models.MovieResults;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by chaitanyap7 on 4/13/16.
 */
public class TmdbConnector {

    private static String TAG = "TmdbConnector";
    private static final String API_KEY = "85380ca322256320bbf2c30c06d6c080";
    public static final String API_URL = "http://api.themoviedb.org";

    public interface MovieListener {
        void updateFilmsList(List<MovieBox> movies);
        void updateMovieInfo(MovieInfo movieInfo);
    }

    List<MovieListener> movieListeners = new ArrayList<>();
    public void addMovieListener (MovieListener ml) {
        movieListeners.add(ml);
    }

    TmdbService movieService;

    public TmdbConnector() {
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieService = retro.create(TmdbService.class);
    }


    //getting information for a single movie class
    public void getMovieDetails(Integer id){
        Call movieInfoCall = movieService.getMovieDetails(id, API_KEY);
        movieInfoCall.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Response<MovieInfo> response) {
                MovieInfo model = response.body();
                if (model == null)
                    return;
                for (MovieListener ml: movieListeners)
                    ml.updateMovieInfo(model);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    //function that returns search results
    public void searchMovies(String movieName){
        try{
            Call moviesCall = movieService.searchMovies(API_KEY, movieName);
            enqueueMovieResults(moviesCall);
        }catch (Exception e){
            String exception = e.getMessage();
        }
    }

    //getting information for a list of movies
    public void getMovies(String requestType){
        try{
            //default set to movies now in theaters
            Call moviesCall = movieService.getNowPlayingMovies(API_KEY);

            //getting the appropriate results from the TMDB API
            if(requestType.equals("Upcoming")) {
                moviesCall = movieService.getUpcomingMovies(API_KEY);
            } else if(requestType.equals("TopRated")) {
                moviesCall = movieService.getTopRatedMovies(API_KEY);
            } else if(requestType.equals("NowPlaying")) {
                moviesCall = movieService.getNowPlayingMovies(API_KEY);
            }

            enqueueMovieResults(moviesCall);
        } catch (Exception e){
            String exception = e.getMessage();
        }
    }

    //helper function to populate movie results
    private void enqueueMovieResults(Call moviesCall){
        moviesCall.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Response<MovieResults> response) {
                MovieResults mResults = response.body();
                if (mResults != null) {
                    //updating the movie results where it was originally called
                    List<MovieBox> myMovies = mResults.getMovies();
                    for (MovieListener ml : movieListeners)
                        ml.updateFilmsList(myMovies);
                }
            }

            @Override
            public void onFailure(Throwable thr) {
                Log.d(TAG, thr.getMessage());
            }
        });
    }
}