package com.example.nathan.movieknight;

/**
 * Created by nathan on 4/6/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieList extends ArrayAdapter<String> implements Filterable {
    private Filter movieFilter;
    private final Activity context;
    private ArrayList<String> movieNames;
    private ArrayList<String> oldMovieNames;
    private  Integer[] movieImage;
    private  Integer[] oldMovieImage;
    public MovieList(Activity context,
                     ArrayList<String>  movieNames, Integer[] movieImage) {

        super(context, R.layout.list_single_movie, movieNames);

        this.context = context;
        this.movieNames = movieNames;
        oldMovieNames = new ArrayList<String>();
        for(String movie : movieNames){
            oldMovieNames.add(movie);
        }
        oldMovieImage = new Integer[movieImage.length];
        this.movieImage = movieImage;
        for(int i = 0; i < movieImage.length;i++){
            oldMovieImage[i] = movieImage[i];
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_movie, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.listmovietxt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listmovieimg);

        if(position<movieNames.size())
            txtTitle.setText(movieNames.get(position));
        if(position < movieImage.length)
            imageView.setImageResource(movieImage[position]);
        return rowView;
    }

 @Override
    public Filter getFilter() {
        if (movieFilter == null)
            movieFilter = new MovieFilter();

        return movieFilter;
    }

    private class MovieFilter extends Filter {
        ArrayList<Integer> positions;
        boolean noMatch = true;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<String> mMovieList = new ArrayList<String>();
            positions = new ArrayList<Integer>();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                noMatch = false;
                // No filter implemented we return all the list
                results.values = oldMovieNames;
                results.count = oldMovieNames.size();
                for(int i = 0; i < oldMovieImage.length; i++){
                    movieImage[i] = oldMovieImage[i];
                }

            }
            else {
                noMatch = true;
                // We perform filtering operation
                int count = 0;
                for (String movie : oldMovieNames) {
                    if (movie.toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        mMovieList.add(movie);
                        positions.add(count);
                    }
                    count++;
                }

                results.values = mMovieList;
                results.count = mMovieList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0){
                notifyDataSetInvalidated();
                clear();
                if(!noMatch){
                    addAll(oldMovieNames);
                    movieNames = oldMovieNames;
                    movieImage = new Integer[oldMovieImage.length];
                    for(int i = 0; i < oldMovieImage.length; i++){
                        movieImage[i] = oldMovieImage[i];
                    }
                }

            }
            else {
                clear();
                movieNames = (ArrayList<String>) results.values;

                addAll(movieNames);
                if(positions.size() > 0){
                    movieImage = new Integer[positions.size()];
                    for(int i = 0; i < positions.size(); i++){
                        movieImage[i] = oldMovieImage[positions.get(i)];
                    }
                }

                notifyDataSetChanged();

            }

        }

    }
}
