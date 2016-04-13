package com.example.nathan.movieknight;

/**
 * Created by natha on 4/6/2016.
 */
        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

public class MovieList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] movieNames;
    private final Integer[] movieImage;
    public MovieList(Activity context,
                      String[] movieNames, Integer[] movieImage) {
        super(context, R.layout.list_single_movie, movieNames);
        this.context = context;
        this.movieNames = movieNames;
        this.movieImage = movieImage;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_movie, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.listmovietxt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listmovieimg);
        txtTitle.setText(movieNames[position]);

        imageView.setImageResource(movieImage[position]);
        return rowView;
    }
}
