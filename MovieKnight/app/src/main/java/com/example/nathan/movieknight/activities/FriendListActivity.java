package com.example.nathan.movieknight.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.models.FriendList;

import java.util.ArrayList;

/**
 * Created by natha on 4/6/2016.
 */
public class FriendListActivity extends AppCompatActivity {
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
    FriendList friendAdapter;
    boolean movieMode = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        lv = (ListView)findViewById(R.id.listView);
        sv = (SearchView)findViewById(R.id.searchView2);
        friendsList = new ArrayList<String>();
        friendsList.add("A");
        friendsList.add("B");
        friendsList.add("C");
        friendsList.add("D");
        friendsList.add("E");

       friendAdapter = new FriendList(this, friendsList, imageId);
        lv.setAdapter(friendAdapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                friendAdapter.getFilter().filter(text);
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }
}
