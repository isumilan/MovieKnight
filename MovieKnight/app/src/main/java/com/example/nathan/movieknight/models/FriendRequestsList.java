package com.example.nathan.movieknight.models;

/**
 * Created by nathan on 4/6/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nathan.movieknight.R;

import java.util.ArrayList;

public class FriendRequestsList extends ArrayAdapter<String>  {
    private final Activity context;
    private ArrayList<String> friendNames;
    private  Integer[] friendImage;

    public FriendRequestsList(Activity context,
                              ArrayList<String> friendNames, Integer[] friendImage) {

        super(context, R.layout.list_single_friend_request, friendNames);

        this.context = context;
        this.friendNames = friendNames;

        this.friendImage = friendImage;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_friend_request, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.listfriendtxt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listfriendimg);

        if(position<friendNames.size())
            txtTitle.setText(friendNames.get(position));
        if(position < friendImage.length)
            imageView.setImageResource(friendImage[position]);
        return rowView;
    }





}
