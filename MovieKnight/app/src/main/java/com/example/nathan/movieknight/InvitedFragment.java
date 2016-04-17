package com.example.nathan.movieknight;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nathan on 3/17/2016.
 */
public class InvitedFragment  extends Fragment  {
    ListView list;
    ArrayList<String> eventList;
    Integer[] imageId = {
            R.drawable.sampai,
            R.drawable.event,
            R.drawable.dango,
            R.drawable.glass,
            R.drawable.home,
            R.drawable.movie
    };
    final EventListActivity eventListActivity;
    @SuppressLint("ValidFragment")
    public InvitedFragment(EventListActivity ea){
        super();
        eventListActivity = ea;
    }
    public InvitedFragment(){
        super();
        eventListActivity = null;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.in_theaters_layout,null);
        eventList = new ArrayList<String>();
        eventList.add("Mad Samuel");
        eventList.add("Inside Out");
        eventList.add("Star Wars");
        eventList.add("The Martian");
        eventList.add("Dango");
        eventList.add("Deadpool");
        EventList adapter = new
                EventList(eventListActivity, eventList, imageId);


        list=(ListView)view.findViewById(R.id.intheaterslistView);
        eventListActivity.setGoingAdapter((adapter));
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if(list != null) {

            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(eventListActivity, "You Clicked at " + eventList.get(+position), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        } else{
            System.out.println("null");
        }

        return view;
    }
}
