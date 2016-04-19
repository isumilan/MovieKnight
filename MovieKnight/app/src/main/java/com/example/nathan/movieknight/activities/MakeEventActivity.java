package com.example.nathan.movieknight.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.nathan.movieknight.MovieKnightAppli;
import com.example.nathan.movieknight.models.FriendList;
import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.MovieEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by natha on 4/6/2016.
 */
public class MakeEventActivity extends NavigationDrawer {
    ListView list;
    ArrayList<String> friendList;
    ArrayList<String> checkedList;
    Integer[] imageId = {
            R.drawable.sampai,
            R.drawable.event,
            R.drawable.dango,
            R.drawable.glass,
            R.drawable.home,
            R.drawable.movie
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);

        Bundle b = getIntent().getExtras();
        final int movieID = b.getInt("movieID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        friendList = new ArrayList<String>();
        checkedList = new ArrayList<String>();
        checkedList.add(((MovieKnightAppli)getApplication()).getUserName());

        friendList.add("Mad Samuel");
        friendList.add("Inside Out");
        friendList.add("Star Wars");
        friendList.add("The Martian");
        friendList.add("Dango");
        friendList.add("Deadpool");
        final FriendList adapter = new
                FriendList(this, friendList, imageId);

        list=(ListView)findViewById(R.id.friendlistView);
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if(list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                    }
                });
            }
        } else{
            System.out.println("null");
        }

        SearchManager searchManager = (SearchManager) MakeEventActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) findViewById(R.id.friendSearchView);

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MakeEventActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String text) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String text) {
                    adapter.getFilter().filter(text);
                    return false;
                }
            });
        }

        Button makeeventbutton = (Button)findViewById(R.id.make_event);
        makeeventbutton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String owner = ((MovieKnightAppli)getApplication()).getUserName();
                        boolean public_private = ((RadioGroup)findViewById(R.id.privacy_radio_group)).indexOfChild(((RadioGroup)findViewById(R.id.privacy_radio_group)).findViewById(((RadioGroup)findViewById(R.id.privacy_radio_group)).getCheckedRadioButtonId())) == 0;
                        String EventTitle = ((EditText)findViewById(R.id.eventTitle)).getText().toString();
                        String time = ((EditText)findViewById(R.id.dateTime)).getText().toString();
                        String location = "The Universe";
                        Vector<String> invitations = new Vector<String>(checkedList);
                        MovieEvent me = null;
                        try {
                            me = ((MovieKnightAppli) getApplication()).getClisten().MakeEventRequest(owner, movieID, public_private, EventTitle, time, location, invitations);
                        } catch (ClassNotFoundException cnfe) {
                            cnfe.printStackTrace();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        } catch (NullPointerException npe) {
                            npe.printStackTrace();
                        }
                        if (me != null) {
                            String eid = me.getEventID();
                            Bundle b = new Bundle();
                            Intent in = new Intent(getApplicationContext(), EventActivity.class);
                            b.putString("key", eid);
                            startActivity(in);
                            finish();
                        }
                    }
                }
        );
    }
}
