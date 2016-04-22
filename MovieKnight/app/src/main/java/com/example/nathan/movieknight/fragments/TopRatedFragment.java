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
import com.example.nathan.movieknight.activities.MovieListActivity;
import com.example.nathan.movieknight.models.MovieList;

import java.util.ArrayList;

/**
 * Created by Nathan on 3/17/2016.
 */
public class TopRatedFragment  extends Fragment  {
    MovieList adapter;
    ListView list;
    ArrayList<String> movieNames;
    ArrayList<String> movieImages;
    ArrayList<Integer> movieID;
    final MovieListActivity movieListActivity;
    @SuppressLint("ValidFragment")
    public TopRatedFragment(MovieListActivity ma){
        super();
        movieListActivity = ma;
        MovieKnightAppli application = (MovieKnightAppli)ma.getApplication();
        movieNames = application.getMovieListTop();
        movieImages = application.getMovieImagesTop();
        movieID = application.getMovieIDTop();
    }
    public TopRatedFragment(){
        super();
        movieListActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.top_rated_layout,null);

        adapter = new MovieList(movieListActivity, movieNames, movieImages);

        list=(ListView)view.findViewById(R.id.topratedlistView);
        movieListActivity.setBluAdapter((adapter));
        if(list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent(movieListActivity.getApplicationContext(), MovieActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("movieID", movieID.get(position));
                        in.putExtras(bundle);
                        startActivity(in);
                        movieListActivity.finish();
                    }
                });
            }
        } else{
            Log.d("TopRatedFragment","null");
        }

        return view;
    }
}
