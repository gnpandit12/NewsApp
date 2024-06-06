package com.example.newsapp.model.room_database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newsapp.model.data.Article;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookmarkDao bookmarkDao();

}
