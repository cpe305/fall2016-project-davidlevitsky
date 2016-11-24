package com.example.davidlevitsky.friendsconnect;

import android.content.Context;
import android.content.Intent;
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
    private ImageButton ibInfo;
    private ImageButton ibSelect;
    private Context context;


    public YelpResultAdapter(Context context, ArrayList<YelpResult> resultsList) {
        super(context, 0, resultsList);
        this.resultsList = resultsList;
        this.context = context;
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
        ibInfo = (ImageButton)convertView.findViewById(R.id.ibYelpResultInfo);
        ibSelect = (ImageButton)convertView.findViewById(R.id.ibYelpResultSelect);
        ibSelect.setTag(position);
        ibInfo.setTag(position);
        // Populate the data into the template view using the data object
        tvYelpResultName.setText(result.getName());
        tvYelpResultRating.setText("Rating (out of 5): " + result.getRating());
        // Return the completed view to render on screen
        String url = result.getPictureURL();
        //replace end of url from ms.jpg to ls.jpg so image has better resolution
        url = url.replace("ms.jpg", "ls.jpg");
        Picasso.with(getContext()).load(url).into(image);

//        ibInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getContext(), YelpResultInfoActivity.class);
//                context.startActivity(i);
//
//            }
//        });



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
