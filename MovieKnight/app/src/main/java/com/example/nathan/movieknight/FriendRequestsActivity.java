package com.example.nathan.movieknight;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by natha on 4/6/2016.
 */
public class FriendRequestsActivity extends NavigationDrawer {
    ListView lv;
    SearchView sv;
    ArrayList<String> friends;
    Integer[] imageId = {
            R.drawable.event,
            R.drawable.dango,
            R.drawable.glass,
            R.drawable.home,
            R.drawable.movie
    };
    ArrayAdapter<String> friendAdapter;
    boolean movieMode = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);
        lv = (ListView)findViewById(R.id.listView);
        sv = (SearchView)findViewById(R.id.searchView2);
        friends = new ArrayList<String>();
        friends.add("Chaitanya");
        friends.add("Isumi");
        friends.add("Kevin");
        friends.add("Nathan");
        friends.add("Samuel");
        final FriendRequestsList friendAdapter = new
                FriendRequestsList(this, friends, imageId);

        lv.setAdapter(friendAdapter);


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
