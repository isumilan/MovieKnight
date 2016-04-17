package com.example.nathan.movieknight.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

<<<<<<< HEAD:MovieKnight/app/src/main/java/com/example/nathan/movieknight/MainActivity.java
import java.io.IOException;
import java.net.Socket;
=======
import com.example.nathan.movieknight.NavigationDrawer;
import com.example.nathan.movieknight.R;
>>>>>>> remotes/movieknights/201-group-22-project-movie-knight/master:MovieKnight/app/src/main/java/com/example/nathan/movieknight/activities/MainActivity.java

public class MainActivity extends NavigationDrawer
       {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*try {
            Socket socket = new Socket("localhost", 5554);
            ClientListener listener = new ClientListener(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
