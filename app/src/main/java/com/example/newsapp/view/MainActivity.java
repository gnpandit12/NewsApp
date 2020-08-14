package com.example.newsapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.model.adapter.NewsAdapter;
import com.example.newsapp.model.constants.Constants;
import com.example.newsapp.model.pojoclass.Article;
import com.example.newsapp.model.pojoclass.NewsAPIResponse;
import com.example.newsapp.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main_activity";
    private MainViewModel mMainViewModel;
    private String country;
    private String category;
    private String apiKey;
    private long totalResults;
    private NewsAdapter newsAdapter;
    private RecyclerView newsRecyclerView;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        country = "us";
        category = "business";
        apiKey = Constants.apiKey;

        newsRecyclerView = findViewById(R.id.news_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "Country: "+country);
        Log.d(TAG, "Category: "+category);
        Log.d(TAG, "apiKey: "+apiKey);
        mMainViewModel = new MainViewModel();
        mMainViewModel.getCountrySpecificNews(country, category, apiKey);
        observeViewModel();
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
}