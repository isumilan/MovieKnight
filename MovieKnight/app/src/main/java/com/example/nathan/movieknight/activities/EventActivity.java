package com.example.nathan.movieknight.activities;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.nathan.movieknight.NavigationDrawer;
import com.example.nathan.movieknight.R;

public class EventActivity extends NavigationDrawer {

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

        //something something Comment area functionality
    }


}
