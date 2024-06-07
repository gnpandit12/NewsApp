package com.example.newsapp.model.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.data.Article;
import com.example.newsapp.model.listener.BookmarkExitsListener;
import com.example.newsapp.model.listener.OnBookmarkClickedListener;
import com.example.newsapp.model.listener.OnHeadlineClickListener;

import java.util.ArrayList;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 21/05/24
 **/
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private Context mContext;
    private ArrayList<Article> mArticleArrayList;
    private OnHeadlineClickListener mOnHeadlineClickListener;
    private OnBookmarkClickedListener mOnBookmarkClickedListener;
    private BookmarkExitsListener mBookmarkExitsListener;
    public NewsRecyclerViewAdapter(Context context, ArrayList<Article> articleArrayList) {
        this.mContext = context;
        this.mArticleArrayList = articleArrayList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.headlines_card_view, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Article article = mArticleArrayList.get(position);

        Glide
                .with(mContext)
                .load(article.getImage())
                .into(holder.newsImageView);

        holder.titleTextView.setText(article.getTitle());
        holder.dateTextView.setText(article.getPublishedAt());

        holder.itemView.setOnClickListener(view -> mOnHeadlineClickListener.onNewsClicked(article));

        holder.bookmarkImageview.setOnClickListener(view -> mOnBookmarkClickedListener.onBookmarkClicked(article, article.getTitle(), article.getContent(), article.getDescription(), holder.bookmarkImageview));

        mBookmarkExitsListener.isBookmarked(article, holder.bookmarkImageview);

    }

    @Override
    public int getItemCount() {
        return mArticleArrayList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImageView;
        TextView titleTextView;
        TextView dateTextView;
        ImageView bookmarkImageview;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsImageView = itemView.findViewById(R.id.news_image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            bookmarkImageview = itemView.findViewById(R.id.bookmark_image_view);

        }
    }

    public void setOnNewsClickListener(OnHeadlineClickListener onNewsClickListener) {
        this.mOnHeadlineClickListener = onNewsClickListener;
    }

    public void setOnBookmarkClickedListener(OnBookmarkClickedListener onBookmarkClickedListener) {
        this.mOnBookmarkClickedListener = onBookmarkClickedListener;
    }

    public void setBookmarkExitsListener(BookmarkExitsListener bookmarkExitsListener) {
        this.mBookmarkExitsListener = bookmarkExitsListener;
    }

}
