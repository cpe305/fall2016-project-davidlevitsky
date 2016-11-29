package com.example.davidlevitsky.friendsconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
    public final int REQUEST_CODE = 200;
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
                //clear previous search results
                yelpResultsList.clear();
                String searchTerm = etYelpSearchTerm.getText().toString();
                String location = etYelpLocation.getText().toString();
                search(searchTerm, location);
            }
        });

        lvYelpResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id){

                Toast toast = Toast.makeText(getApplicationContext(), "HI", Toast.LENGTH_SHORT);
                toast.show();

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
            ArrayList<Business> businesses = searchResponse.businesses();
          //  String rating = Double.toString(businesses.get(0).rating());  // 4.0
          //  String address
          //  Toast toast2 = Toast.makeText(getApplicationContext(), "name: " + businessName + "rating: " + Double.toString(rating), Toast.LENGTH_LONG);
          //  toast2.show();
            for (Business b : businesses) {
                String name = b.name();
                String rating = Double.toString(b.rating());
                ArrayList<String> addressList = b.location().address();
                //get the street and property number
                String address = addressList.get(0);
                String url = b.imageUrl();
                String numReviews = Integer.toString(b.reviewCount());
                String description = b.snippetText();
                String mobileURL = b.mobileUrl();

                YelpResult res = new YelpResult(name, rating, address, url, numReviews, description, mobileURL);
                yelpResultsList.add(res);
            }
            mYelpResultsAdapter.notifyDataSetChanged();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void onSubmit(View v) {
        // EditText etName = (EditText) findViewById(R.id.name);
        // Prepare data intent
        Intent data = new Intent();
        int position = (Integer)v.getTag();
        YelpResult result = yelpResultsList.get(position);
        // Pass relevant data back as a result
        data.putExtra("name", result.getName());
        data.putExtra("location", result.getAddress());
        data.putExtra("url", result.getPictureURL());
        data.putExtra("rating", result.getRating());
        data.putExtra("code", 200); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    public void onInformationClick(View v) {
        Intent data = new Intent(SearchEventYelpActivity.this, YelpResultInfoActivity.class);
        int position = (Integer)v.getTag();
        YelpResult result = yelpResultsList.get(position);
        data.putExtra("name", result.getName());
        data.putExtra("address", result.getAddress());
        String url = result.getPictureURL();
        //replace end of url from ms.jpg to ls.jpg so image has better resolution
        url = url.replace("ms.jpg", "l.jpg");
        data.putExtra("url", url);
        data.putExtra("rating", result.getRating());
        data.putExtra("description", result.getDescription());
        data.putExtra("mobileURL", result.getMobileURL());
        data.putExtra("numReviews", result.getNumReviews());
        startActivity(data);
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
