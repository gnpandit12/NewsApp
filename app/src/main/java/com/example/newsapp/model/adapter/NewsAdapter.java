package com.example.newsapp.model.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.newsapp.R;
import com.example.newsapp.model.pojoclass.Article;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    public static final String TAG = "news_adapter";
    private Context mContext;
    private List<Article> mArticleList;

    public NewsAdapter(Context context, List<Article> articleList){
        this.mContext = context;
        this.mArticleList = articleList;
        Log.d(TAG, "NewsAdapter: ");
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_card, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Article article = mArticleList.get(position);

        holder.sourceTextView.setText(article.getSource().getName());
        holder.titleTextView.setText(article.getTitle());
        holder.authorTextView.setText(article.getAuthor());

        Glide.with(mContext)
                .load(article.getUrlToImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        assert e != null;
                        Log.d(TAG, "onLoadFailed: "+e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: ");
                        return false;
                    }
                }).into(holder.newsImageView);

        holder.descriptionTextView.setText(article.getDescription());

    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView sourceTextView, titleTextView, authorTextView, descriptionTextView;
        ImageView newsImageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            sourceTextView = itemView.findViewById(R.id.source_text_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            authorTextView = itemView.findViewById(R.id.author_text_view);
            descriptionTextView = itemView.findViewById(R.id.news_description_text_view);
            newsImageView = itemView.findViewById(R.id.news_image_view);

        }



    }


}
