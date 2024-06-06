package com.example.newsapp.model.listener;

import android.widget.ImageView;

import com.example.newsapp.model.data.Article;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public interface OnBookmarkClickedListener {

    void onBookmarkClicked(Article article, String title, String content, String description, ImageView bookmarkImageView);
}
