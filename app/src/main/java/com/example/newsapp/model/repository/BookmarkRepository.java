package com.example.newsapp.model.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.newsapp.model.data.Article;
import com.example.newsapp.model.room_database.AppDatabase;
import com.example.newsapp.model.room_database.BookmarkDao;

import java.util.List;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class BookmarkRepository {

    AppDatabase appDatabase;
    BookmarkDao bookmarkDao;
    private LiveData<List<Article>> articlesListLiveData;

    public BookmarkRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database_name")
                .fallbackToDestructiveMigration()
                .build();

        bookmarkDao = appDatabase.bookmarkDao();
        articlesListLiveData = bookmarkDao.getBookmarkedArticles();

    }

    public void insertArticle(Article article) {
        bookmarkDao.insertArticle(article);
    }

    public void updateArticle(Article article) {
        bookmarkDao.updateArticle(article);
    }

    public void deleteArticle(Article article) {
        bookmarkDao.deleteArticle(article);
    }

    public LiveData<Integer> isBookmarkExits(Article article) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        AsyncTask.execute(() -> {
            int count = bookmarkDao.isArticleExists(article.getTitle(), article.getContent(), article.getDescription());
            data.postValue(count);
        });
        return data;
    }

    public LiveData<List<Article>> getAllBookmarks(){
        return articlesListLiveData;
    }

}
