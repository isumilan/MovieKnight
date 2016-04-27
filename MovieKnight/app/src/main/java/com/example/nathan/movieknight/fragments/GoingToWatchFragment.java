package com.example.nathan.movieknight.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.activities.MovieActivity;
import com.example.nathan.movieknight.activities.ProfileMovieListActivity;
import com.example.nathan.movieknight.models.MovieInfo;
import com.example.nathan.movieknight.models.MovieList;
import com.example.nathan.movieknight.tmdb.TmdbConnector;
import com.example.nathan.movieknight.tmdb.TmdbService;

import java.util.Vector;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Nathan on 3/17/2016.
 */
public class GoingToWatchFragment  extends Fragment  {
    ListView list;
    Vector<Integer> movieID;
    Vector<String> movieList;
    Vector<String> movieImages;
    final ProfileMovieListActivity profileMovieListActivity;
    @SuppressLint("ValidFragment")
    public GoingToWatchFragment(ProfileMovieListActivity ea){
        super();
        profileMovieListActivity = ea;
    }
    public GoingToWatchFragment(){
        super();
        profileMovieListActivity = null;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.in_theaters_layout, null);
        movieID = ((MovieKnightAppli)profileMovieListActivity.getApplication()).getUserProfile().getToWatch();
        movieList = ((MovieKnightAppli) profileMovieListActivity.getApplication()).getUserProfile().getToWatchName();
       if(movieList == null){
           movieList = new Vector<String>();
       }
        if (movieID == null){
            movieID = new Vector<Integer>();
        }
        movieImages = new Vector<String>();
        MovieList adapter = new
                MovieList(profileMovieListActivity, movieList, movieImages);


        list = (ListView) view.findViewById(R.id.intheaterslistView);
        profileMovieListActivity.setGoingToWatchAdapter((adapter));
        MovieKnightAppli application = (MovieKnightAppli)profileMovieListActivity.getApplication();
        application.setCurrentContext(inflater.getContext());
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if (list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent(profileMovieListActivity.getApplicationContext(), MovieActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("movieID", movieID.get(position));
                        in.putExtras(b);
                        startActivity(in);
                    //    profileMovieListActivity.finish();
                    }
                });
            }
        } else {
            System.out.println("null");
        }

        return view;
    }
}
