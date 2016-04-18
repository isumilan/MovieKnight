package com.example.nathan.movieknight.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nathan.movieknight.R;
import com.example.nathan.movieknight.activities.MovieListActivity;
import com.example.nathan.movieknight.tmdb.TmdbConnector;

import java.util.ArrayList;

public class MovieTabFragment extends Fragment {

    ArrayList<String> movieList_top;
    ArrayList<String> movieImages_top;
    ArrayList<Integer> movieID_top;

    //In theaters
    ArrayList<String> movieList_in;
    ArrayList<String> movieImages_in;
    ArrayList<Integer> movieID_in;

    ArrayList<String> movieList_upcoming;
    ArrayList<String> movieImages_upcoming;
    ArrayList<Integer> movieID_upcoming;
    TmdbConnector tmdbConnector;




    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    MovieListActivity movieListActivity;
    @SuppressLint("ValidFragment")
    public MovieTabFragment(MovieListActivity ma){
        super();
        movieListActivity = ma;


        //getting the information for different class objects
        movieList_top = new ArrayList<String>();
        movieImages_top = new ArrayList<String>();
        movieID_top = new ArrayList<Integer>();

        movieList_in = new ArrayList<String>();
        movieImages_in = new ArrayList<String>();
        movieID_in = new ArrayList<Integer>();

        movieList_upcoming = new ArrayList<String>();
        movieImages_upcoming = new ArrayList<String>();
        movieID_upcoming = new ArrayList<Integer>();

        tmdbConnector = new TmdbConnector();
        tmdbConnector.getMovies("TopRated",movieList_top,movieImages_top,movieID_top);
        tmdbConnector.getMovies("Upcoming",movieList_upcoming,movieImages_upcoming,movieID_upcoming);
        tmdbConnector.getMovies("NowPlaying", movieList_in, movieImages_in, movieID_in);

        Log.d("Top", "" + movieList_top.size());
        Log.d("Upcoming",""+movieList_upcoming.size());
        Log.d("Now",""+movieList_in.size());





    }
    public MovieTabFragment(){
        super();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(), movieListActivity));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {
        MovieListActivity movieListActivity;
        public MyAdapter(FragmentManager fm, MovieListActivity ma) {
            super(fm);
            movieListActivity = ma;
        }

        /**
         * Return fragment with respect to Position .
         */
/*

 */
        @Override
        public Fragment getItem(int position)
        {

            switch (position){
                case 0 : return new InTheatersFragment(movieListActivity,movieList_in,movieImages_in,movieID_in, tmdbConnector);
                case 1 : return new ComingSoonFragment(movieListActivity, movieList_upcoming, movieImages_upcoming, movieID_upcoming, tmdbConnector);
                case 2 : return new TopRatedFragment(movieListActivity, movieList_top, movieImages_top, movieID_top, tmdbConnector);
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "In Theaters";
                case 1 :
                    return "Coming Soon";
                case 2 :
                    return "Top Rated";
            }
            return null;
        }
    }

}