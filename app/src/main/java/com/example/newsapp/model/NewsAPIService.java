package com.example.newsapp.model;

import com.example.newsapp.model.di.ApiComponent;
import com.example.newsapp.model.di.ApiModule;
import com.example.newsapp.model.di.DaggerApiComponent;
import com.example.newsapp.model.pojoclass.NewsAPIResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsAPIService {

    @Inject
    NewsAPI newsAPI;
    private ApiComponent component;
    public NewsAPIService(){
        component = DaggerApiComponent.builder()
                .apiModule(new ApiModule()).build();
        component.inject(this);
    }

    public Single<NewsAPIResponse> getCountrySpecificNews(
            String country,
            String category,
            String apiKey

    ){
        return newsAPI.getCountrySpecificNews(country, category, apiKey);
    }

}
