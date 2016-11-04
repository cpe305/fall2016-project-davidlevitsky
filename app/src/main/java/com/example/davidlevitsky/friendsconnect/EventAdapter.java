package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by davidlevitsky on 10/16/16.
 * This class is a custom Array Adapter to create a graphical interface
 * to view events.
 */
public class EventAdapter extends ArrayAdapter<Event>{
    public EventAdapter(Context context, ArrayList<Event> eventsList) {
        super(context, 0, eventsList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        if (event == null) {
            return convertView;
        }
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_adapter, parent, false);
        }
        // Lookup view for data population
        TextView tvEventName = (TextView)convertView.findViewById(R.id.tvAdapterEventName);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvAdapterDate);
        // Populate the data into the template view using the data object
        tvEventName.setText(event.getName());
        tvDate.setText(event.getDate());
        // Return the completed view to render on screen
        return convertView;
    }

}
