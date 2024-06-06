package com.example.newsapp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.databinding.FragmentTopHeadlinesBinding;
import com.example.newsapp.model.Constants;
import com.example.newsapp.model.adapter.NewsRecyclerViewAdapter;
import com.example.newsapp.model.data.Article;
import com.example.newsapp.model.data.TopHeadlines;
import com.example.newsapp.model.listener.OnBookmarkClickedListener;
import com.example.newsapp.model.listener.OnHeadlineClickListener;
import com.example.newsapp.view.NewsDetailsActivity;
import com.example.newsapp.viewModel.BookmarkViewModel;
import com.example.newsapp.viewModel.TopHeadlinesViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class TopHeadlinesFragment extends Fragment implements OnHeadlineClickListener, OnBookmarkClickedListener {

    private FragmentTopHeadlinesBinding fragmentTopHeadlinesBinding;
    private TopHeadlinesViewModel topHeadlinesViewModel;

    private RecyclerView newsRecyclerView;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private ProgressBar progressBar;
    private EditText searchEditText;
    public static final String TAG = "TopHeadlinesFragment";
    private BookmarkViewModel bookmarkViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topHeadlinesViewModel = new ViewModelProvider(requireActivity()).get(TopHeadlinesViewModel.class);
        bookmarkViewModel = new ViewModelProvider(requireActivity()).get(BookmarkViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentTopHeadlinesBinding = FragmentTopHeadlinesBinding.inflate(inflater, container, false);
        return fragmentTopHeadlinesBinding.getRoot();


    }

    @Override
    public void onStart() {
        super.onStart();
        newsRecyclerView = fragmentTopHeadlinesBinding.headlinesRecyclerView;
        progressBar = fragmentTopHeadlinesBinding.progressBar;
        searchEditText = fragmentTopHeadlinesBinding.searchEditText;

        searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (!searchEditText.getText().toString().trim().isEmpty()) {
                    getSearchedNews(
                            searchEditText.getText().toString().trim(),
                            "en",
                            "10",
                            Constants.API_KEY
                    );
                } else {
                    Log.d(TAG, "Search null");
                }
            }
            return false;
        });

        progressBar.setVisibility(View.VISIBLE);
        if (!searchEditText.getText().toString().trim().isEmpty()) {
            getSearchedNews(
                    searchEditText.getText().toString().trim(),
                    "en",
                    "10",
                    Constants.API_KEY
            );
        } else {
            getTopHeadlines("technology", "en", "10", Constants.API_KEY);
        }
    }

    private void getTopHeadlines(String category, String language, String maxHeadlines, String apiKey) {

        topHeadlinesViewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        topHeadlinesViewModel.getTopHeadlines(category, language, maxHeadlines, apiKey).observe(this, topHeadlines -> {
            if (topHeadlines != null) {
                displayHeadlines(topHeadlines);
            } else {
                Log.d(TAG, "Response null");
            }
        });

    }

    private void getSearchedNews(
            String searchKeyword,
            String lang,
            String max,
            String apikey
    ) {

        topHeadlinesViewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        topHeadlinesViewModel.getSearchedNews(searchKeyword, lang, max, apikey).observe(this, topHeadlines -> {
            if (topHeadlines != null) {
                displayHeadlines(topHeadlines);
            } else {
                Log.d(TAG, "Response null");
            }
        });

    }

    private void displayHeadlines(TopHeadlines topHeadlines) {
        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getContext(), (ArrayList<Article>) topHeadlines.getArticles());
        newsRecyclerView.setAdapter(newsRecyclerViewAdapter);
        newsRecyclerViewAdapter.notifyDataSetChanged();
        newsRecyclerViewAdapter.setOnNewsClickListener(this);
        newsRecyclerViewAdapter.setOnBookmarkClickedListener(this);
    }

    @Override
    public void onNewsClicked(Article article) {
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }

    @Override
    public void onBookmarkClicked(Article article, String title, String content, String description, ImageView bookmarkImageView) {
        bookmarkViewModel.isBookmarkExits(article).observe(requireActivity(), integer -> {
            if (integer == 0) {
                // Add bookmark
                bookmarkViewModel.insertArticle(article);
                bookmarkImageView.setImageResource(R.drawable.baseline_bookmark_24);
            } else if (integer > 0) {
                // Remove bookmark
                bookmarkImageView.setImageResource(R.drawable.baseline_bookmark_border_24);
                bookmarkViewModel.deleteArticle(article);
            }
        });
    }
}