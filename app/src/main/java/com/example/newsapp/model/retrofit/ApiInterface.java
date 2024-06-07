package com.example.newsapp.model.retrofit;

import com.example.newsapp.model.data.TopHeadlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 20/05/24
 **/
public interface ApiInterface {

    @GET("/api/v4/top-headlines?")
    Call<TopHeadlines> getTopHeadlines(
            @Query("category") String category,
            @Query("lang") String lang,
            @Query("country") String country,
            @Query("max") String max,
            @Query("apikey") String apikey
    );

    @GET("/api/v4/search?")
    Call<TopHeadlines> getSearchedHeadlines(
            @Query("q") String searchKeyword,
            @Query("lang") String lang,
            @Query("country") String country,
            @Query("max") String max,
            @Query("apikey") String apikey
    );

}
