package com.example.newsapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.model.adapter.NewsAdapter;
import com.example.newsapp.model.constants.Constants;
import com.example.newsapp.model.location.LocationService;
import com.example.newsapp.model.pojoclass.Article;
import com.example.newsapp.model.pojoclass.NewsAPIResponse;
import com.example.newsapp.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";
    private MainViewModel mMainViewModel;
    private String country;
    private String category;
    private String apiKey;
    private long totalResults;
    private NewsAdapter newsAdapter;
    private RecyclerView newsRecyclerView;
    private String country_name = "in";

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationService locationService;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        checkLocationPermission();
        category = "business";
        apiKey = Constants.apiKey;

        newsRecyclerView = findViewById(R.id.news_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "Category: "+category);
        Log.d(TAG, "apiKey: "+apiKey);
        mMainViewModel = new MainViewModel();

        locationService = new LocationService(MainActivity.this);
        if (locationService.canGetLocation()) {
            double longitude = locationService.getLongitude();
            double latitude = locationService.getLatitude();
            country_name = locationService.getCountryName(this, latitude, longitude);
            if ("India".equals(country_name)){
                country_name = "in";
            }else if ("United States".equals(country_name)){
                country_name = "us";
            }
            Log.d(TAG, "Country: "+country_name);
            mMainViewModel.getCountrySpecificNews(country_name, category, apiKey);
            observeViewModel();
        } else {
            locationService.showSettingsAlert();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        locationService = new LocationService(MainActivity.this);
        if (locationService.canGetLocation()) {
            double longitude = locationService.getLongitude();
            double latitude = locationService.getLatitude();
            country_name = locationService.getCountryName(this, latitude, longitude);
            if ("India".equals(country_name)){
                country_name = "in";
            }else if ("United States".equals(country_name)){
                country_name = "us";
            }
            Log.d(TAG, "Country: "+country_name);
            mMainViewModel.getCountrySpecificNews(country_name, category, apiKey);
            observeViewModel();
        } else {
            locationService.showSettingsAlert();
        }
    }

    private void observeViewModel() {
        mMainViewModel.newsAPIResponseMutableLiveData.observe(
                this, new Observer<NewsAPIResponse>() {
                    @Override
                    public void onChanged(NewsAPIResponse newsAPIResponse) {
                        Log.d(TAG, "onChanged: ");
                        String status = newsAPIResponse.getStatus();
                        if ("ok".equals(status)){
                            totalResults = newsAPIResponse.getTotalResults();
                            Log.d(TAG, "Total Results: "+totalResults);
                            newsAdapter = new NewsAdapter(MainActivity.this, newsAPIResponse.getArticles());
                            newsRecyclerView.setAdapter(newsAdapter);
                            newsAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MainActivity.this, "Status: "+status, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ALL_PERMISSIONS_RESULT);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ALL_PERMISSIONS_RESULT);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == ALL_PERMISSIONS_RESULT) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationService = new LocationService(MainActivity.this);
                    if (locationService.canGetLocation()) {
                        double longitude = locationService.getLongitude();
                        double latitude = locationService.getLatitude();
                        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                    } else {
                        locationService.showSettingsAlert();
                    }

                }

            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.stopListener();
    }


}