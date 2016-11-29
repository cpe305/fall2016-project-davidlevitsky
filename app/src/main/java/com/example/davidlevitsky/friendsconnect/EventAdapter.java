package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by davidlevitsky on 10/16/16.
 * This class is a custom Array Adapter to create a graphical interface
 * to view events.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    Realm realm;
    ArrayList<Event> eventsList;

    public EventAdapter(Context context, ArrayList<Event> eventsList) {
        super(context, 0, eventsList);
        this.eventsList = eventsList;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RealmSetup();

        // Get the data item for this position
        Event event = getItem(position);
        if (event == null) {
            return convertView;
        }
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_adapter, parent, false);
        }
        final ImageButton ibDeleteEvent = (ImageButton) convertView.findViewById(R.id.ibDeleteEvent);
        ibDeleteEvent.setTag(position);
        ImageButton ibViewInfo = (ImageButton)convertView.findViewById(R.id.ibAdapterViewEventDetails);
        ibViewInfo.setTag(position);


        // Lookup view for data population
        TextView tvEventName = (TextView)convertView.findViewById(R.id.tvAdapterEventName);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvAdapterDate);
        TextView tvFromTime = (TextView)convertView.findViewById(R.id.tvFromTime);
        TextView tvLocation = (TextView)convertView.findViewById(R.id.tvLocation);
        TextView tvToTime = (TextView)convertView.findViewById(R.id.tvToTime);
        ImageView iv = (ImageView)convertView.findViewById(R.id.ivAdapterEventImage);


        // Populate the data into the template view using the data object
        tvEventName.setText(event.getName());
        tvDate.setText(event.getDate());
        tvLocation.setText(event.getLocation());
        tvFromTime.setText("Start Time: " + event.getFromTime());
        tvToTime.setText("End Time: " + event.getToTime());
        Picasso.with(getContext()).load(event.getImageUrl()).into(iv);
        // Return the completed view to render on screen

        //ImageButton btnDeleteEvent = (ImageButton)convertView.findViewById(R.id.ibDeleteEvent);

        //not working


        return convertView;
    }

    public void RealmSetup() {
        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();
     //   Toast.makeText(getContext(), "\nNumber of events: " + realm.where(Event.class).count(), Toast.LENGTH_SHORT).show();

    }

}
