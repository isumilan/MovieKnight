package com.example.nathan.movieknight.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.activities.MovieActivity;
import com.example.nathan.movieknight.activities.MovieListActivity;
import com.example.nathan.movieknight.models.MovieList;
import com.example.nathan.movieknight.tmdb.TmdbConnector;

import java.util.ArrayList;

/**
 * Created by Nathan on 3/17/2016.
 */
public class InTheatersFragment  extends Fragment  {
    MovieList adapter;
    ListView list;
    ArrayList<String> movieList;
    ArrayList<String> movieImages;
    ArrayList<Integer> movieID;
    TmdbConnector tmdbConnector;
    final MovieListActivity movieListActivity;
    @SuppressLint("ValidFragment")
    public InTheatersFragment(MovieListActivity ma, ArrayList<String> movieList, ArrayList<String> movieImages,ArrayList<Integer> movieID,  TmdbConnector tmdbConnector){
        super();
        movieListActivity = ma;
        this.movieList = movieList;
        this.movieImages = movieImages;
        this.movieID = movieID;
        this.tmdbConnector = tmdbConnector;
        tmdbConnector.setTheatersFragment(this);
    }
    public InTheatersFragment(){
        super();
        movieListActivity = null;
    }
    public ArrayList<String> getMovieList(){
        return movieList;
    }
    public ArrayList<String> getMovieImages(){
        return movieImages;
    }
    public ArrayList<Integer> getMovieID(){
        return movieID;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.in_theaters_layout,null);


        list=(ListView) view.findViewById(R.id.intheaterslistView);
        adapter = new
                MovieList(movieListActivity, movieList, movieImages);

        movieListActivity.setTheatersAdapter((adapter));
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if(list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent(movieListActivity.getApplicationContext(), MovieActivity.class);
                        Bundle b = new Bundle();
                        b.putString("key", movieList.get(position));
                        in.putExtras(b);
                        startActivity(in);
                        movieListActivity.finish();
                    }
                });
            }
        } else{
            System.out.println("null");
        }

        return view;
    }
}
