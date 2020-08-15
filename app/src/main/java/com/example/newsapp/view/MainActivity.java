package com.example.newsapp.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.model.adapter.NewsAdapter;
import com.example.newsapp.model.constants.Constants;
import com.example.newsapp.model.location.LocationService;

import com.example.newsapp.viewModel.MainViewModel;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
    private double currentLatitude, currentLongitude;
    private ProgressBar circularProgressBar;

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationService locationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circularProgressBar =  findViewById(R.id.progress_circular);
        circularProgressBar.setVisibility(View.VISIBLE);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        category = "business";
        apiKey = Constants.apiKey;
        newsRecyclerView = findViewById(R.id.news_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "Category: "+category);
        Log.d(TAG, "apiKey: "+apiKey);
        mMainViewModel = new MainViewModel();

    }

    @Override
    protected void onResume() {
        super.onResume();
        circularProgressBar.setVisibility(View.VISIBLE);
        getLocation();
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Log.d(TAG, "run: ");
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    country_name = address.getCountryName();
                    Log.d(TAG, "Country Name: "+country_name);
                    mMainViewModel.getCountrySpecificNews(country_name, category, apiKey);
                    observeViewModel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 3000);

    }


    private void observeViewModel() {
        mMainViewModel.newsAPIResponseMutableLiveData.observe(
                this, newsAPIResponse -> {
                    circularProgressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onChanged: ");
                    String status = newsAPIResponse.getStatus();
                    if ("ok".equals(status)){
                        totalResults = newsAPIResponse.getTotalResults();
                        Log.d(TAG, "Total Results: "+totalResults);
                        newsAdapter = new NewsAdapter(MainActivity.this, newsAPIResponse.getArticles());
                        newsRecyclerView.setAdapter(newsAdapter);
                        newsAdapter.notifyDataSetChanged();
                    }else {
                        circularProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Status: "+status, Toast.LENGTH_SHORT).show();
                    }
                }
        );
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

                    getLocation();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        Log.d(TAG, "run: ");
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                            if (addresses.size() > 0) {
                                Address address = addresses.get(0);
                                country_name = address.getCountryName();
                                Log.d(TAG, "Country Name: "+country_name);
                                mMainViewModel.getCountrySpecificNews(country_name, category, apiKey);
                                observeViewModel();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }, 2500);

                }

            }
        }else {
            Log.d(TAG, "onRequestPermissionsResult: ");
        }
    }


    public void getLocation(){
        locationService = new LocationService(MainActivity.this);
        if(locationService.canGetLocation()){
            currentLatitude = locationService.getLatitude();
            currentLongitude = locationService.getLongitude();
        }else{
            locationService.showSettingsAlert();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.stopUsingGPS();
    }


}