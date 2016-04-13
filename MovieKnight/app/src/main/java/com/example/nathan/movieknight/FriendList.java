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

public class FriendList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] friendNames;
    private final Integer[] friendImage;
    public FriendList(Activity context,
                     String[] friendNames, Integer[] friendImage) {
        super(context, R.layout.list_single_friend, friendNames);
        this.context = context;
        this.friendNames = friendNames;
        this.friendImage = friendImage;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_friend, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.listfriendtxt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listfriendimg);
        txtTitle.setText(friendNames[position]);

        imageView.setImageResource(friendImage[position]);
        return rowView;
    }
}
