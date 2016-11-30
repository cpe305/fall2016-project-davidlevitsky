package com.example.davidlevitsky.friendsconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayEventInfoActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvLocation;
    TextView tvAddress;
    TextView tvRating;
    TextView tvParticipant;
    TextView tvFromTime;
    TextView tvToTime;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_info);
        setup();
        //populate fields based on Intent information sent from MainActivity
        String name = getIntent().getStringExtra("name");
        String location = getIntent().getStringExtra("location");
        String address = getIntent().getStringExtra("address");
        String rating = getIntent().getStringExtra("rating");
        String participant = getIntent().getStringExtra("participant");
        String fromTime = getIntent().getStringExtra("fromTime");
        String toTime = getIntent().getStringExtra("toTime");
        String url = getIntent().getStringExtra("url");
        //user could have inputted a custom location, which won't have an image URL from Yelp
        if (url != null) {
            //modify the URL to display a bigger image
            url = url.replace("ms.jpg", "ls.jpg");
            Picasso.with(getApplicationContext()).load(url).into(image);
        }
        tvName.setText(name);
        tvLocation.setText(location);
        tvAddress.setText("Address: " + address);
        tvParticipant.setText("Invitee: " + participant);
        tvFromTime.setText("From: " + fromTime);
        tvToTime.setText("To: " + toTime);
        if (rating != null) {
            tvRating.setText("Rating (out of 5): " + rating);
        }



    }

    public void setup() {
        tvName = (TextView)findViewById(R.id.tvDisplayInfoName);
        tvLocation = (TextView)findViewById(R.id.tvDisplayInfoLocation);
        tvAddress= (TextView)findViewById(R.id.tvDisplayInfoAddress);
        tvRating= (TextView)findViewById(R.id.tvDisplayInfoRating);
        tvParticipant= (TextView)findViewById(R.id.tvDisplayInfoParticipant);
        tvFromTime= (TextView)findViewById(R.id.tvDisplayInfoFromTime);
        tvToTime = (TextView)findViewById(R.id.tvDisplayInfoToTime);
        image = (ImageView)findViewById(R.id.ivDisplayInfoImage);
    }

}
