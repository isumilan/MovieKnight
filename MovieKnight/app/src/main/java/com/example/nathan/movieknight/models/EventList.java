package com.example.nathan.movieknight.models;

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

import com.example.nathan.movieknight.R;

import java.util.ArrayList;

public class EventList extends ArrayAdapter<String> implements Filterable {
    private Filter eventFilter;
    private final Activity context;
    private ArrayList<String> eventNames;
    private ArrayList<String> oldeventNames;
    private  Integer[] eventImage;
    private  Integer[] oldeventImage;
    public EventList(Activity context,
                     ArrayList<String>  eventNames, Integer[] eventImage) {

        super(context, R.layout.list_single_event, eventNames);

        this.context = context;
        this.eventNames = eventNames;
        oldeventNames = new ArrayList<String>();
        for(String event : eventNames){
            oldeventNames.add(event);
        }
        oldeventImage = new Integer[eventImage.length];
        this.eventImage = eventImage;
        for(int i = 0; i < eventImage.length;i++){
            oldeventImage[i] = eventImage[i];
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_event, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.listeventtxt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listeventimg);

        if(position<eventNames.size())
            txtTitle.setText(eventNames.get(position));
        if(position < eventImage.length)
            imageView.setImageResource(eventImage[position]);
        return rowView;
    }

    @Override
    public Filter getFilter() {
        if (eventFilter == null)
            eventFilter = new eventFilter();

        return eventFilter;
    }

    private class eventFilter extends Filter {
        ArrayList<Integer> positions;
        boolean noMatch = true;
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<String> meventList = new ArrayList<String>();
            positions = new ArrayList<Integer>();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                noMatch = false;
                // No filter implemented we return all the list
                results.values = oldeventNames;
                results.count = oldeventNames.size();
                for(int i = 0; i < oldeventImage.length; i++){
                    eventImage[i] = oldeventImage[i];
                }

            }
            else {
                noMatch = true;
                // We perform filtering operation
                int count = 0;
                for (String event : oldeventNames) {
                    if (event.toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        meventList.add(event);
                        positions.add(count);
                    }
                    count++;
                }

                results.values = meventList;
                results.count = meventList.size();

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
                    addAll(oldeventNames);
                    eventNames = oldeventNames;
                    eventImage = new Integer[oldeventImage.length];
                    for(int i = 0; i < oldeventImage.length; i++){
                        eventImage[i] = oldeventImage[i];
                    }
                }

            }
            else {
                clear();
                eventNames = (ArrayList<String>) results.values;

                addAll(eventNames);
                if(positions.size() > 0){
                    eventImage = new Integer[positions.size()];
                    for(int i = 0; i < positions.size(); i++){
                        eventImage[i] = oldeventImage[positions.get(i)];
                    }
                }

                notifyDataSetChanged();

            }

        }

    }




}
