package com.example.davidlevitsky.friendsconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class YelpResultInfoActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvAddress;
    ImageView ivInfoPicture;
    TextView tvRating;
    TextView tvNumReviews;
    TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_result_info);
        setup();

    }

    public void setup() {
        tvName = (TextView)findViewById(R.id.tvInfoName);
        tvAddress = (TextView)findViewById(R.id.tvInfoAddress);
        ivInfoPicture = (ImageView) findViewById(R.id.ivInfoImage);
        tvRating = (TextView)findViewById(R.id.tvInfoRating);
        tvNumReviews = (TextView)findViewById(R.id.tvInfoNumReviews);

        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String url = getIntent().getStringExtra("url");
        String rating = getIntent().getStringExtra("rating");
        String numReviews = getIntent().getStringExtra("numReviews");
        String description = getIntent().getStringExtra("description");
        String mobileURL = getIntent().getStringExtra("mobileURL");

        tvName.setText(name);
        tvAddress.setText("Address: " + address);
        Picasso.with(getApplicationContext()).load(url).into(ivInfoPicture);
        tvRating.setText("Rating (out of 5): " + rating);
        tvNumReviews.setText("Number of Reviews: " + numReviews);

    }
}
