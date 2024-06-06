package com.example.newsapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newsapp.databinding.ActivityNewsDetailsBinding;
import com.example.newsapp.model.data.Article;

public class NewsDetailsActivity extends AppCompatActivity {

    public static final String TAG = "NewsDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNewsDetailsBinding activityNewsDetailsBinding = ActivityNewsDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityNewsDetailsBinding.getRoot());

        ImageView newsDetailsImageView = activityNewsDetailsBinding.newsDetailsImageView;
        TextView titleTextView = activityNewsDetailsBinding.newsDetailsTitleTextView;
        TextView authorTextView = activityNewsDetailsBinding.authorTextView;
        TextView descriptionTextView = activityNewsDetailsBinding.descriptionTextView;
        TextView contentTextView = activityNewsDetailsBinding.contentTextView;


        if (getIntent().getSerializableExtra("article") != null) {
            Log.d(TAG, "Get Intent");
            Article mArticle = (Article) getIntent().getSerializableExtra("article");

            if (mArticle != null) {
                Log.d(TAG, "Article not null");
                titleTextView.setText(mArticle.getTitle());
                authorTextView.setText(mArticle.getSource().getName());
                descriptionTextView.setText(mArticle.getDescription());
                contentTextView.setText(mArticle.getContent());

                Glide
                        .with(this)
                        .load(mArticle.getImage())
                        .into(newsDetailsImageView);

                Log.d(TAG, "News title: "+mArticle.getTitle());

            } else {
                Log.d(TAG, "Article object null");
            }
        } else {
            Log.d(TAG, "Article object null");
        }

    }
}