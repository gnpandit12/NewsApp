package com.example.newsapp.model.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 20/05/24
 **/
public class RetrofitService {

    private static final String BASE_URL = "https://gnews.io";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiInterface getInterface() {
        return retrofit.create(ApiInterface.class);
    }

}
