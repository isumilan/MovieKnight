package com.example.nathan.movieknight.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.NavigationDrawer;
import com.example.nathan.movieknight.R;

public class ProfileActivity extends NavigationDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //this button should apppear for other users but not self
        //ie getIntent.getExtras.getBoolean(friend) == true
        Button addfriendbutton = (Button)findViewById(R.id.addFriendButton);
        addfriendbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //sends a friend request
                        //makes a popup notifying success
                    }
                }
        );

        //this button should apppear for self but not other users
        //ie getIntent.getExtras.getBoolean(friend) == false
        Button editbutton = (Button)findViewById(R.id.editButton);
        editbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //open up some kind of editing interface
                        //stuff to edit: (in order of priority)
                        //Profile Description
                        //Favorite Movies
                        //Profile Image
                     //   ((EditText) findViewById(R.id.profile_description)).setFocusable(true);
                   //     ((EditText) findViewById(R.id.profile_description)).setTextIsSelectable(true);

                    }
                }
        );

        Button friendlistbutton = (Button)findViewById(R.id.friendListButton);
        friendlistbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), FriendListActivity.class));
                    }
                }
        );


        Button movielistbutton = (Button)findViewById(R.id.movieListButton);
        movielistbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //open up the movie list activity
                        startActivity(new Intent(getApplicationContext(), ProfileMovieListActivity.class));
                    }
                }
        );
    }


}
