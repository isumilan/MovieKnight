package com.example.nathan.movieknight.activities;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nathan.movieknight.ClientListener;
import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.MovieEvent;
import com.example.nathan.movieknight.models.MovieInfo;
import com.example.nathan.movieknight.tmdb.TmdbConnector;
import com.example.nathan.movieknight.tmdb.TmdbService;
import com.squareup.picasso.Picasso;

import java.util.Vector;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class EventActivity extends NavigationDrawer {
    private String eventID;
    private int movieID;
    private ImageView eventImage;
    TextView eventTitle;
    TextView movieTitle;
    TextView date;
    TextView theater;
    TextView owner;
    Button goingButton;
    Button notGoingButton;
    ListView invitedList;
    ListView goingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //This activity also should have differing functionality based on other user vs. self

        Button editbutton = (Button)findViewById(R.id.customizeButton);
        editbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //if you are the owner
                        //open up some kind of editing interface
                        //stuff to edit: (in order of priority)
                        //Title
                        //Date/Time
                        //Theater
                        //Friends Invited

                        //the editing interface also has to keep track of the event ID
                        //else
                        //open up a choice betweeen "going" and "interested" and "not going"
                    }
                }
        );
        Button goingButton= (Button)findViewById(R.id.goingButton);
        editbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                    }
                }
        );
        Button notGoingButton= (Button)findViewById(R.id.notgoingButton);
        editbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                    }
                }
        );
        invitedList = (ListView)findViewById(R.id.invitedListView);
        goingList = (ListView)findViewById(R.id.goingListView);

        eventTitle = (TextView) findViewById(R.id.eventTitle);
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        date = (TextView) findViewById(R.id.dateTime);
        theater = (TextView) findViewById(R.id.movieTheater);
        owner = (TextView) findViewById(R.id.movieOwner);
        eventImage = (ImageView) findViewById(R.id.eventImage);
        Bundle b = getIntent().getExtras();

        if(b != null) {
            eventID = b.getString("eventID");
            MovieKnightAppli application = (MovieKnightAppli) getApplication();
            Object[] objects = {"Movie Event Request", eventID};
            ClientListener cl = application.getClisten();
            if (cl != null) {
                MovieEvent movieEvent = (MovieEvent) cl.clientRequest(objects);
                if (movieEvent != null) {
                    eventTitle.setText(movieEvent.getDescription());
                    date.setText(movieEvent.getMovieTime());
                    theater.setText(movieEvent.getTheater());
                    owner.setText(movieEvent.getOwner());
                    movieID = movieEvent.getGoingToWatch();
                    getMovieInfo(movieID);
                    goingButton.setVisibility(View.GONE);
                    notGoingButton.setVisibility(View.GONE);
                    Vector<String> invited =  movieEvent.getInvited();
                    ArrayAdapter<String> invitedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invited);
                    invitedList.setAdapter(invitedAdapter);
                    Vector<String> going =  movieEvent.getParticipants();
                    ArrayAdapter<String> goingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, going);
                    goingList.setAdapter(goingAdapter);
                }
            }
        }

        else{
            //do something maybe
        }

        //something something Comment area functionality
    }

    private void getMovieInfo(int id) {

        TmdbService movieService = ((MovieKnightAppli)getApplication()).getMovieService();
//        Log.d("movieID is", ""+id);

        Call<MovieInfo> infoCall = movieService.getMovieDetails(id, TmdbConnector.API_KEY);

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
        movieTitle.setText(info.getTitle());
        Picasso.with(this)
                .load(info.getPosterPath())
                .into(eventImage);
    }
}
