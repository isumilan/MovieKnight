package com.example.nathan.movieknight.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    private String movieNameString;
    int movieID;
    private ProgressDialog progress_dialog;

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
        final MovieKnightAppli application = (MovieKnightAppli)getApplication();
        application.setCurrentContext(this);
        Bundle b = getIntent().getExtras();
        movieID = b.getInt("movieID");

        setupMoviePage();
        displayProgressMessage();
        getMovieInfo(movieID);

        Button makeeventbutton = (Button)findViewById(R.id.makeEventButton);
        makeeventbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(application.isGuest()){
                            PopUp();

                        } else{
                            //open up the movie list activity
                            Bundle b = new Bundle();
                            b.putInt("movieID", movieID);
                            b.putString("movieName", movieNameString);
                            Intent in = new Intent(getApplicationContext(), MakeEventActivity.class);
                            in.putExtras(b);
                            startActivity(in);
                            finish();
                        }

                    }
                }
        );
        Button addFavoritesButton = (Button) findViewById(R.id.addFavoritesButton);
        addFavoritesButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if(application.isGuest()){
                            PopUp();
                        } else {
                            // send the favorites information up to the server
                            startActivity(new Intent(getApplication(), ProfileActivity.class));
                            finish();
                        }
                    }
                }
        );
        Button addWatchListButton = (Button) findViewById(R.id.addWatchlistButton);
        addWatchListButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if(application.isGuest()){
                            PopUp();
                        } else {
                            AskForDest();
                        }
                    }
                }
        );
    }

    void AskForDest() {
        AlertDialog.Builder movielistBuilder = new AlertDialog.Builder(this);
        movielistBuilder.setTitle("Add Movie As...");
        movielistBuilder.setPositiveButton("Watched",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add the movie to WATCHED list
                        startActivity(new Intent(getApplication(), ProfileMovieListActivity.class));
                    }
                });
        movielistBuilder.setNegativeButton("To Watch",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add the movie to TO WATCH list
                        startActivity(new Intent(getApplication(), ProfileMovieListActivity.class));
                    }
                });
        AlertDialog movielistDialog = movielistBuilder.create();
        movielistDialog.show();
    }

    void PopUp(){
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("YOU ARE A GUEST");
        helpBuilder.setMessage("Cannot access as guest. Buy our app for $4.99");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
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
                if(info != null)
                    setupDetails(info);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setupDetails(MovieInfo info) {

        movieName.setText(info.getTitle());
        movieNameString = info.getTitle();
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

        progress_dialog.dismiss();
    }


    private void displayProgressMessage() {
        progress_dialog = new ProgressDialog(MovieActivity.this);
        progress_dialog.setMessage("Loading..");
        progress_dialog.show();
    }

}
