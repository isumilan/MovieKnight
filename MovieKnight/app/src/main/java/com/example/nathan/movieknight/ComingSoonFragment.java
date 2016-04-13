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

/**
 * Created by Nathan on 3/17/2016.
 */
public class ComingSoonFragment  extends Fragment  {
    ListView list;
    String[] movieList = {"Title: Mad Samuel \nGenre: Samuel",
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
   final MoviesActivity moviesActivity;
    @SuppressLint("ValidFragment")
    public ComingSoonFragment(MoviesActivity ma){
        super();
        moviesActivity = ma;
    }
    public ComingSoonFragment(){
        super();
        moviesActivity = null;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.coming_soon_layout,null);
        MovieList adapter = new
                MovieList(moviesActivity, movieList, imageId);


        list=(ListView)view.findViewById(R.id.comingsoonlistView);
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
                        Toast.makeText(moviesActivity, "You Clicked at " + movieList[+position], Toast.LENGTH_SHORT).show();

                    }
                });
            }
        } else{
            System.out.println("null");
        }

        return view;
    }
}
