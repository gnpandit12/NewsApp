package com.example.newsapp.model.di;

import com.example.newsapp.model.NewsAPI;
import com.example.newsapp.model.NewsAPIService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private String BASE_URL = "http://newsapi.org";

    @Provides
    NewsAPI provideCountriesAPI(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(NewsAPI.class);
    }

    @Provides
    NewsAPIService provideCountriesService(){
        return new NewsAPIService();
    }



}
