package com.example.newsapp.model.di;

import com.example.newsapp.model.NewsAPIService;
import com.example.newsapp.viewModel.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApiModule.class)
public
interface ApiComponent {

    void inject(NewsAPIService newsAPIService);
    void inject(MainViewModel mainViewModel);


}
