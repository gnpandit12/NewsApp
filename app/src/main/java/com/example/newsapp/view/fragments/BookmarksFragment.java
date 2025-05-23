package com.example.newsapp.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.databinding.FragmentBookmarkBinding;
import com.example.newsapp.model.adapter.NewsRecyclerViewAdapter;
import com.example.newsapp.model.data.Article;
import com.example.newsapp.model.listener.BookmarkExitsListener;
import com.example.newsapp.model.listener.OnBookmarkClickedListener;
import com.example.newsapp.model.listener.OnHeadlineClickListener;
import com.example.newsapp.view.NewsDetailsActivity;
import com.example.newsapp.viewModel.BookmarkViewModel;

import java.util.ArrayList;
import java.util.List;


public class BookmarksFragment extends Fragment implements OnHeadlineClickListener, OnBookmarkClickedListener, BookmarkExitsListener {

    private FragmentBookmarkBinding fragmentBookmarkBinding;
    private BookmarkViewModel bookmarkViewModel;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private ProgressBar progressBar;
    private RecyclerView bookmarksRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookmarkViewModel = new ViewModelProvider(requireActivity()).get(BookmarkViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(inflater,container,false);
        return fragmentBookmarkBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        bookmarksRecyclerView = fragmentBookmarkBinding.bookmarksRecyclerView;
        progressBar = fragmentBookmarkBinding.bookmarksProgressBar;
        progressBar.setVisibility(View.VISIBLE);

        getBookmarks();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getBookmarks() {
        bookmarkViewModel.getAllBookmarksList().observe(requireActivity(), articles -> {
            newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(getContext(), (ArrayList<Article>) articles);
            bookmarksRecyclerView.setAdapter(newsRecyclerViewAdapter);
            newsRecyclerViewAdapter.setOnNewsClickListener(this);
            newsRecyclerViewAdapter.setOnBookmarkClickedListener(this);
            newsRecyclerViewAdapter.setBookmarkExitsListener(this);
            progressBar.setVisibility(View.INVISIBLE);
            newsRecyclerViewAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onNewsClicked(Article article) {
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }

    @Override
    public void onBookmarkClicked(Article article, String title, String content, String description, ImageView bookmarkImageView) {
        bookmarkViewModel.deleteArticle(title);
    }

    @Override
    public void isBookmarked(Article article, ImageView bookmarkImageView) {
        bookmarkImageView.setImageResource(R.drawable.baseline_bookmark_24);
    }
}