package com.example.nathan.movieknight.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.NavigationDrawer;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.MovieInfo;
import com.example.nathan.movieknight.tmdb.TmdbConnector;
import com.example.nathan.movieknight.tmdb.TmdbService;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class MovieActivity extends NavigationDrawer {

    private TextView movieName;
    private ImageView moviePoster;
    private TextView movieRating;
    private TextView movieReleaseDate;
    private TextView movieRuntime;
    private TextView movieSynopsis;

    int movieID;

    private DateFormat movieDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Bundle b = getIntent().getExtras();
        movieID = b.getInt("movieID");

        setupMoviePage();
        getMovieInfo(movieID);

        Button makeeventbutton = (Button)findViewById(R.id.makeEventButton);
        makeeventbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //open up the movie list activity
                        Bundle b = new Bundle();
                        b.putInt("movieID", 1);
                        Intent in = new Intent(getApplicationContext(), MakeEventActivity.class);
                        in.putExtras(b);
                        startActivity(in);
                        finish();
                    }
                }
        );
    }

    private void setupMoviePage() {

        movieName = (TextView) findViewById(R.id.movieTitle);

        moviePoster = (ImageView) findViewById(R.id.moviePoster);

        movieRating = (TextView) findViewById(R.id.movieRated);

        movieReleaseDate = (TextView) findViewById(R.id.movieReleaseDate);

        movieRuntime = (TextView) findViewById(R.id.movieRuntime);

        movieSynopsis = (TextView) findViewById(R.id.movieSynopsis);

        movieDateFormat = android.text.format.DateFormat.getDateFormat(this);
    }


    private void getMovieInfo(int id) {

        TmdbService movieService = ((MovieKnightAppli)getApplication()).getMovieService();
//        Log.d("movieID is", ""+id);

        Call<MovieInfo> infoCall = movieService.getMovieDetails(id,TmdbConnector.API_KEY);

        infoCall.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Response<MovieInfo> response) {
                MovieInfo info = response.body();
                setupDetails(info);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setupDetails(MovieInfo info) {

        movieName.setText(info.getTitle());

        movieRating.setText("Rating: "+info.getVoteAverage() + "/10" + "("+info.getVoteCount()+" votes)");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date movDate = sdf.parse(info.getReleaseDate());
            String releaseDate = movieDateFormat.format(movDate);
            movieReleaseDate.setText("Release Date: " + releaseDate);
        } catch (java.text.ParseException e) {
        }

        movieRuntime.setText("Runtime: "+info.getRuntime() + " min");

        movieSynopsis.setText(info.getOverview());

        Picasso.with(this)
                .load(info.getPosterPath())
                .into(moviePoster);
    }
}
