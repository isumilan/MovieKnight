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

import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.activities.MovieActivity;
import com.example.nathan.movieknight.activities.ProfileMovieListActivity;
import com.example.nathan.movieknight.models.MovieList;

import java.util.ArrayList;

/**
 * Created by Nathan on 3/17/2016.
 */
public class WatchedFragment  extends Fragment  {
    ListView list;
    ArrayList<String> movieList;
ArrayList<String> movieImages;
    final ProfileMovieListActivity profileMovieListActivity;
    @SuppressLint("ValidFragment")
    public WatchedFragment(ProfileMovieListActivity ea){
        super();
        profileMovieListActivity = ea;
    }
    public WatchedFragment(){
        super();
        profileMovieListActivity = null;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.in_theaters_layout,null);
        movieList = new ArrayList<String>();
        movieList.add("Mad Samuel");
        movieList.add("Inside Out");
        movieList.add("Star Wars");
        movieList.add("The Martian");
        movieList.add("Dango");
        movieList.add("Deadpool");
        movieImages = new ArrayList<String>();
        MovieKnightAppli application = (MovieKnightAppli)profileMovieListActivity.getApplication();
        application.setCurrentContext(inflater.getContext());
       MovieList adapter = new
               MovieList(profileMovieListActivity, movieList, movieImages);


        list=(ListView)view.findViewById(R.id.intheaterslistView);
        profileMovieListActivity.setGoingToWatchAdapter((adapter));
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if(list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent(  profileMovieListActivity.getApplicationContext(), MovieActivity.class);
                        Bundle b = new Bundle();
                        b.putString("key", movieList.get(position));
                        in.putExtras(b);
                        startActivity(in);
                        profileMovieListActivity.finish();
                    }
                });
            }
        } else{
            System.out.println("null");
        }

        return view;
    }
}
