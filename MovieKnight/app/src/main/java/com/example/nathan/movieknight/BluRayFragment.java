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
public class BluRayFragment  extends Fragment  {
    ListView list;
    ArrayList<String> movieList;
    Integer[] imageId = {
            R.drawable.sampai,
            R.drawable.event,
            R.drawable.dango,
            R.drawable.glass,
            R.drawable.home,
            R.drawable.movie
    };
    final MoviesActivity moviesActivity;
    @SuppressLint("ValidFragment")
    public BluRayFragment (MoviesActivity ma){
        super();
        moviesActivity = ma;
    }
    public BluRayFragment (){
        super();
        moviesActivity = null;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.blu_ray_layout,null);
        movieList = new ArrayList<String>();

        movieList.add("Mad Samuel");
        movieList.add("Inside Out");
        movieList.add("Star Wars");
        movieList.add("The Martian");
        movieList.add("Dango");
        movieList.add("Deadpool");

         

        MovieList adapter = new
                MovieList(moviesActivity, movieList, imageId);


        list=(ListView)view.findViewById(R.id.bluraylistView);
        moviesActivity.setBluAdapter((adapter));
        //list not showing
        //  list=(ListView) LayoutInflater.from(getApplication()).inflate(R.layout.coming_soon_layout, null);
        if(list != null) {
            if (adapter != null) {
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(moviesActivity, "You Clicked at " + movieList.get(+position), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        } else{
            System.out.println("null");
        }

        return view;
    }
}
