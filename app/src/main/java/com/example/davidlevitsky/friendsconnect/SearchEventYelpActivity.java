package com.example.davidlevitsky.friendsconnect;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class SearchEventYelpActivity extends AppCompatActivity {
    private final String CONSUMER_KEY= "ZvHAdI9vVMFywK193WST5g";
    private final String CONSUMER_SECRET = "WacH3p5K75bFz2YL6ooJKDGFIlU";
    private final String TOKEN = "P0QAwcTA6wxRt77viZno2Ov8wDCzWtAR";
    private final String TOKEN_SECRET = "xcRAN6Xn69pQKHy008HJyYaeTGM";
    private Button bSearch;
    private EditText etYelpSearchTerm;
    private EditText etYelpLocation;
    ArrayList<YelpResult> yelpResultsList;
    ListView lvYelpResults;
    YelpResultAdapter mYelpResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event_yelp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setup();
    }

    public void setup() {
        yelpResultsList = new ArrayList<YelpResult>();
        lvYelpResults = (ListView)findViewById(R.id.lvYelpResults);
        mYelpResultsAdapter = new YelpResultAdapter(this, yelpResultsList);
        lvYelpResults.setAdapter(mYelpResultsAdapter);

        bSearch = (Button)findViewById(R.id.bYelpSearch);
        etYelpSearchTerm = (EditText)findViewById(R.id.etYelpSearchTerm);
        etYelpLocation = (EditText)findViewById(R.id.etYelpLocation);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = etYelpSearchTerm.getText().toString();
                String location = etYelpLocation.getText().toString();
                search(searchTerm, location);
            }
        });
    }

    public void search(String searchTerm, String location) {
        // (consumerKey, consumerSecret, token, tokenSecret
        YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpAPI = apiFactory.createAPI();
        Map<String, String> params = new HashMap<>();

        // general params
        params.put("term", searchTerm);
        params.put("limit", "10");

        // locale params
        params.put("lang", "fr");
        // StackOverflow fix to allow Network access from Main thread
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Call<SearchResponse> call = yelpAPI.search(location, params);

        try {
            SearchResponse searchResponse = call.execute().body();
            int totalNumberOfResult = searchResponse.total();  // 3
            ArrayList<Business> businesses = searchResponse.businesses();
            String businessName = businesses.get(0).name();  // "JapaCurry Truck"
          //  String rating = Double.toString(businesses.get(0).rating());  // 4.0
          //  String address
          //  Toast toast2 = Toast.makeText(getApplicationContext(), "name: " + businessName + "rating: " + Double.toString(rating), Toast.LENGTH_LONG);
          //  toast2.show();
            for (Business b : businesses) {
                String name = b.name();
                String rating = Double.toString(b.rating());
                String address = b.location().crossStreets();
                String url = b.imageUrl();

                YelpResult res = new YelpResult(name, rating, address, url);
                yelpResultsList.add(res);
            }
            mYelpResultsAdapter.notifyDataSetChanged();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public EditText getEtYelpSearchTerm() {
        return etYelpSearchTerm;
    }

    public void setEtYelpSearchTerm(EditText etYelpSearchTerm) {
        this.etYelpSearchTerm = etYelpSearchTerm;
    }

    public EditText getEtYelpLocation() {
        return etYelpLocation;
    }

    public void setEtYelpLocation(EditText etYelpLocation) {
        this.etYelpLocation = etYelpLocation;
    }


}
