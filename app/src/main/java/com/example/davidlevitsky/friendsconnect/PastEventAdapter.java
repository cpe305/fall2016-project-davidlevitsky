package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.RealmObject;

/**
 * Created by davidlevitsky on 11/25/16.
 */

public class PastEventAdapter extends ArrayAdapter<Event> {
    Realm realm;
    ArrayList<Event> eventsList;

    public PastEventAdapter(Context context, ArrayList<Event> eventsList) {
        super(context, 0, eventsList);
        this.eventsList = eventsList;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Event event = getItem(position);
        if (event == null) {
            return convertView;
        }
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.past_event_adapter, parent, false);
        }
        // Lookup view for data population
        TextView tvEventName = (TextView)convertView.findViewById(R.id.tvPastAdapterEventName);
        TextView tvDate = (TextView)convertView.findViewById(R.id.tvPastAdapterDate);
        TextView tvFromTime = (TextView)convertView.findViewById(R.id.tvPastFromTime);
        TextView tvLocation = (TextView)convertView.findViewById(R.id.tvPastLocation);
        TextView tvToTime = (TextView)convertView.findViewById(R.id.tvPastToTime);
        ImageView iv = (ImageView)convertView.findViewById(R.id.ivPastAdapterEventImage);


        // Populate the data into the template view using the data object
        tvEventName.setText(event.getName());
        tvDate.setText(event.getDate());
        tvLocation.setText(event.getLocation());
        tvFromTime.setText("Start Time: " + event.getFromTime());
        tvToTime.setText("End Time: " + event.getToTime());
        String url = event.getImageUrl();
        if (url != null) {
            Picasso.with(getContext()).load(url).into(iv);
        }
        // Return the completed view to render on screen

        //ImageButton btnDeleteEvent = (ImageButton)convertView.findViewById(R.id.ibDeleteEvent);
        final ImageButton ibApprove = (ImageButton)convertView.findViewById(R.id.ibRateEvent);
        String userRating = event.getUserRating();
        if (userRating != null && userRating.equals("good")) {
            ibApprove.setImageResource(R.drawable.thumbs_up);
        }
        else if (userRating != null && userRating.equals("bad")){
            ibApprove.setImageResource(R.drawable.thumbs_down);
        }

        ibApprove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (event.getUserRating() == null) {
                    setEventUserRating(event, "good");
                    ibApprove.setImageResource(R.drawable.thumbs_up);
                }
                else if (event.getUserRating().equals("good")) {
                    setEventUserRating(event, "bad");
                    ibApprove.setImageResource(R.drawable.thumbs_down);
                }
                else {
                    setEventUserRating(event, "good");
                    ibApprove.setImageResource(R.drawable.thumbs_up);
                }
            }
        });

        return convertView;
    }

    public void setEventUserRating(final Event event, final String rating) {
        Realm realm = Realm.getDefaultInstance();
        final Event eventToUpdate = realm.where(Event.class)
                .equalTo("name", event.getName())
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                eventToUpdate.setUserRating(rating);

            }
        });
      //  realm.close();
        this.notifyDataSetChanged();
    }
}
