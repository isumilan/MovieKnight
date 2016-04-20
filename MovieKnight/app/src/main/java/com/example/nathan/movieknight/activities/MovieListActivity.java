package com.example.nathan.movieknight.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.nathan.movieknight.fragments.MovieTabFragment;
import com.example.nathan.movieknight.R;


public class MovieListActivity extends NavigationDrawer {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    ArrayAdapter<String> bluAdapter;
    ArrayAdapter<String> comingAdapter;
    ArrayAdapter<String> theatersAdapter;

    String filterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new MovieTabFragment(this)).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setBluAdapter(ArrayAdapter<String> adpater){
        bluAdapter = adpater;
    }
    public void setComingAdapter(ArrayAdapter<String> adpater){
        comingAdapter = adpater;
    }
    public void setTheatersAdapter(ArrayAdapter<String> adpater){
        theatersAdapter = adpater;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_movies, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MovieListActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MovieListActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String text) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String text) {
                    filterText = text;
                    updateAdapters();
                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }
    public void updateAdapters(){
        if(bluAdapter != null)
            bluAdapter.getFilter().filter(filterText);
        if(comingAdapter != null)
            comingAdapter.getFilter().filter(filterText);
        if(theatersAdapter != null)
            theatersAdapter.getFilter().filter(filterText);
    }
}

