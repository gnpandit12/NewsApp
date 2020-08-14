package com.example.newsapp.model;

import com.example.newsapp.model.pojoclass.NewsAPIResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NewsAPI {
    String apiKey = "e372c005422f4a68b66634d86606901c";

    @GET("/v2/top-headlines")
    Single<NewsAPIResponse> getCountrySpecificNews(
            @Query("country")  String country,
            @Query("category") String business,
            @Query("apiKey") String apiKey
    );

}
