package com.example.nathan.movieknight.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.nathan.movieknight.NavigationDrawer;
import com.example.nathan.movieknight.R;

public class MovieActivity extends NavigationDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        String name = b.getString("key");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Button makeeventbutton = (Button)findViewById(R.id.makeEventButton);
        makeeventbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //open up the movie list activity
                        startActivity(new Intent(getApplicationContext(), MakeEventActivity.class));
                        finish();
                    }
                }
        );
    }


}
