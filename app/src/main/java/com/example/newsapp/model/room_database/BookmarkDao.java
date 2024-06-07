package com.example.newsapp.model.room_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newsapp.model.data.Article;

import java.util.List;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/

@Dao
public interface BookmarkDao {


    @Query("SELECT * FROM  Article")
    LiveData<List<Article>> getBookmarkedArticles();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertArticle(Article... articles);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(Article articles);

    @Query("DELETE FROM Article WHERE title =:title ")
    void deleteArticle(String title);

    @Query("SELECT COUNT() FROM Article WHERE title =:title and content =:content and description =:description")
    int isArticleExists(String title, String content, String description);

}
