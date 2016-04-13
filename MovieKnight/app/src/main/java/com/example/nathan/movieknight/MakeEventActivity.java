package com.example.nathan.movieknight;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by natha on 4/6/2016.
 */
public class MakeEventActivity extends NavigationDrawer {
    ListView list;
    String[] friendList = {"Title: Mad Samuel \nGenre: Samuel",
            "Inside Out", "Star Wars", "The Martian", "Dango", "Deadpool"};
    Integer[] imageId = {
            R.drawable.sampai,
            R.drawable.sampai,
            R.drawable.sampai,
            R.drawable.sampai,
            R.drawable.sampai,
            R.drawable.sampai,
            R.drawable.sampai

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FriendList adapter = new
                FriendList(this, friendList, imageId);


        list=(ListView)findViewById(R.id.friendlistView);
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if(list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);

                System.out.println("hi");
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(MakeEventActivity.this, "You Clicked at " + friendList[+position], Toast.LENGTH_SHORT).show();

                    }
                });
            }
        } else{
            System.out.println("null");
        }

    }
}
