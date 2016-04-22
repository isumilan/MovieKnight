package com.example.nathan.movieknight.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.FriendRequestsList;

import java.util.ArrayList;

/**
 * Created by natha on 4/6/2016.
 */
public class FriendRequestsActivity extends NavigationDrawer {
    ListView lv;
    SearchView sv;
    ArrayList<String> friendsList;
    Integer[] imageId = {
            R.drawable.dango,
            R.drawable.event,
            R.drawable.dango,
            R.drawable.glass,
            R.drawable.home,
    };
    boolean movieMode = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        lv = (ListView)findViewById(R.id.listView);

        friendsList = new ArrayList<String>();
        friendsList.add("A");
        friendsList.add("B");
        friendsList.add("C");
        friendsList.add("D");
        friendsList.add("E");
        FriendRequestsList adapter = new FriendRequestsList(this, friendsList, imageId);
        lv.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
