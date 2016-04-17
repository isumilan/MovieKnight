package com.example.nathan.movieknight;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nathan.movieknight.activities.EventListActivity;
import com.example.nathan.movieknight.activities.FriendRequestsActivity;
import com.example.nathan.movieknight.activities.MainActivity;
import com.example.nathan.movieknight.activities.MakeEventActivity;
import com.example.nathan.movieknight.activities.MovieListActivity;
import com.example.nathan.movieknight.activities.ProfileActivity;
import com.example.nathan.movieknight.activities.SearchActivity;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            // Handle the camera action
        }else if(id == R.id.nav_search){
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            finish();
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        } else if (id == R.id.nav_movies) {
            startActivity(new Intent(getApplicationContext(), MovieListActivity.class));
            finish();
        } else if (id == R.id.nav_events) {
            startActivity(new Intent(getApplicationContext(), EventListActivity.class));
            finish();
        } else if (id == R.id.nav_make_event) {
            startActivity(new Intent(getApplicationContext(), MakeEventActivity.class));
            finish();
        } else if(id == R.id.nav_friend_requests){
            startActivity(new Intent(getApplicationContext(), FriendRequestsActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
