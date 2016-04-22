package com.example.nathan.movieknight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nathan.movieknight.R;

/**
 * Created by natha on 4/6/2016.
// */
public class SearchActivity extends NavigationDrawer {
    ListView lv;
    SearchView sv;
    String[] movies;
    String[] friends;

    ArrayAdapter<String> movieAdapter;
    ArrayAdapter<String> friendAdapter;
    boolean movieMode = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lv = (ListView)findViewById(R.id.listView);
        sv = (SearchView)findViewById(R.id.searchView2);

        if (movies != null)
            movieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
        if (friends != null)
            friendAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);
        lv.setAdapter(movieAdapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                if(movieMode && text.length() > 0 && text.substring(0,1).equals("@")){
                    lv.setAdapter(friendAdapter);
                    movieMode = false;
                } else if(text.length() == 0 || (!movieMode &&  text.length() > 0 && !text.substring(0,1).equals("@"))){
                    lv.setAdapter(movieAdapter);
                    movieMode = true;
                }
                if(movieMode){
                    movieAdapter.getFilter().filter(text);
                } else{
                    if(text.length() > 0) {
                        friendAdapter.getFilter().filter(text.substring(1,text.length()));
                    }
                }
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bundle b = new Bundle();
                Intent in;
                if (movieMode) {
                    in = new Intent(getApplicationContext(), MovieActivity.class);
                    b.putString("key", movieAdapter.getItem(position));
                }
                else {
                    in = new Intent(getApplicationContext(), ProfileActivity.class);
                    b.putString("key", friendAdapter.getItem(position));
                    b.putBoolean("friend", true);
                }
                in.putExtras(b);
                startActivity(in);
                finish();
            }
        });

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
