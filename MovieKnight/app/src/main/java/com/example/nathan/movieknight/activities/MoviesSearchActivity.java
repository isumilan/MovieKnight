package com.example.nathan.movieknight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.MovieList;

import java.util.ArrayList;

public class MoviesSearchActivity extends NavigationDrawer {


    MovieList adapter;
    ListView list;

    ArrayList<String> movieNames;
    ArrayList<String> movieImages;
    ArrayList<Integer> movieIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MovieKnightAppli application = (MovieKnightAppli)getApplication();
        application.setCurrentContext(this);
        movieNames = new ArrayList<String>();
        movieImages = new ArrayList<String>();
        movieIDs = new ArrayList<Integer>();

        Bundle b = getIntent().getExtras();
        movieIDs = b.getIntegerArrayList("movieIDs");
        movieNames = b.getStringArrayList("movieNames");
        movieImages = b.getStringArrayList("movieImages");

        View view = this.findViewById(android.R.id.content);
        list = (ListView) view.findViewById(R.id.movieListView);


        adapter = new MovieList(this, movieNames, movieImages);

        if (list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent(getApplicationContext(), MovieActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("movieID", movieIDs.get(position));
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                });
            }
        } else {
            Log.d("TopRatedFragment", "null");
        }
    }

}