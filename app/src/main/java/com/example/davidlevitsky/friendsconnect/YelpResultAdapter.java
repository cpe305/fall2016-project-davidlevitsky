package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by davidlevitsky on 11/23/16.
 * This class is a custom Array Adapter to create a graphical interface
 * to view Yelp Results.
 */
public class YelpResultAdapter extends ArrayAdapter<YelpResult> {

    Realm realm;
    ArrayList<YelpResult> resultsList;
    private TextView tvYelpResultName;
    private TextView tvYelpResultRating;
    private ImageView image;

    public YelpResultAdapter(Context context, ArrayList<YelpResult> resultsList) {
        super(context, 0, resultsList);
        this.resultsList = resultsList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RealmSetup();
        YelpResult result = getItem(position);
        if (result == null) {
            return convertView;
        }
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.yelp_result_adapter, parent, false);
        }
       // final ImageButton ibDeleteEvent = (ImageButton) convertView.findViewById(R.id.ibDeleteEvent);
       // ibDeleteEvent.setTag(position);


        // Lookup view for data population
        tvYelpResultName = (TextView)convertView.findViewById(R.id.tvYelpResultName);
        tvYelpResultRating = (TextView)convertView.findViewById(R.id.tvYelpResultRating);
        image = (ImageView)convertView.findViewById(R.id.ivYelpImage);
        // Populate the data into the template view using the data object
        tvYelpResultName.setText(result.getName());
        tvYelpResultRating.setText(result.getRating());
        // Return the completed view to render on screen
        Picasso.with(getContext()).load(result.getPictureURL()).into(image);


        return convertView;
    }

    // provides a way to access the Realm Database
    public void RealmSetup() {
        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();

    }

}
