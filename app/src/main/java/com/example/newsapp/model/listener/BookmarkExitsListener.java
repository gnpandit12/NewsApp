package com.example.newsapp.model.listener;

import android.media.Image;
import android.widget.ImageView;

import com.example.newsapp.model.data.Article;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 07/06/24
 **/
public interface BookmarkExitsListener {

    void isBookmarked(Article article, ImageView bookmarkImageView);

}
