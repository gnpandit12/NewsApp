package com.example.newsapp.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsapp.model.data.Article;
import com.example.newsapp.model.repository.BookmarkRepository;

import java.util.List;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class BookmarkViewModel extends AndroidViewModel {


    private final BookmarkRepository bookmarkRepository;
    private final LiveData<List<Article>> articleListLiveData;
    public BookmarkViewModel(@NonNull Application application) {
        super(application);

        bookmarkRepository = new BookmarkRepository(application);
        articleListLiveData = bookmarkRepository.getAllBookmarks();

    }

    public LiveData<List<Article>> getAllBookmarksList() {
        return articleListLiveData;
    }

    public void insertArticle(Article article) {
        AsyncTask.execute(() -> bookmarkRepository.insertArticle(article));
    }

    public void updateArticle(Article article) {
        AsyncTask.execute(() -> bookmarkRepository.updateArticle(article));
    }

    public void deleteArticle(String title) {
        AsyncTask.execute(() -> bookmarkRepository.deleteArticle(title));
    }

    public LiveData<Integer> isBookmarkExits(Article article) {
        return bookmarkRepository.isBookmarkExits(article);
    }

}
