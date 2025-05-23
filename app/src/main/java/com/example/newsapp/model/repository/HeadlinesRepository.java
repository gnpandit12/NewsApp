package com.example.newsapp.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.data.TopHeadlines;
import com.example.newsapp.model.retrofit.ApiInterface;
import com.example.newsapp.model.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class HeadlinesRepository {

    private static ApiInterface apiInterface;
    private final MutableLiveData<TopHeadlines> topHeadlinesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<TopHeadlines> searchedNewsMutableLiveData = new MutableLiveData<>();
    private static HeadlinesRepository headlinesRepository;
    public static HeadlinesRepository getInstance() {
        if (headlinesRepository == null){
            headlinesRepository = new HeadlinesRepository();
        }
        return headlinesRepository;
    }

    public HeadlinesRepository(){
        apiInterface = RetrofitService.getInterface();
    }

    public MutableLiveData<TopHeadlines> getTopHeadlinesResponse(
            String category,
            String language,
            String country,
            String maxHeadlines,
            String apiKey
    ) {
        isLoading.setValue(true);
        Call<TopHeadlines> topHeadlinesCall = apiInterface.getTopHeadlines(
              category,
                language,
                country,
                maxHeadlines,
                apiKey
        );

        topHeadlinesCall.enqueue(new Callback<TopHeadlines>() {
            @Override
            public void onResponse(@NonNull Call<TopHeadlines> call, @NonNull Response<TopHeadlines> response) {
                topHeadlinesMutableLiveData.setValue(response.body());
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<TopHeadlines> call, @NonNull Throwable throwable) {
                topHeadlinesMutableLiveData.setValue(null);
            }
        });
        return topHeadlinesMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public MutableLiveData<TopHeadlines> getSearchedNewsResponse(
            String searchKeyword,
            String lang,
            String country,
            String max,
            String apikey
    ) {
        isLoading.setValue(true);
        Call<TopHeadlines> topHeadlinesCall = apiInterface.getSearchedHeadlines(
                searchKeyword,
                lang,
                country,
                max,
                apikey
        );

        topHeadlinesCall.enqueue(new Callback<TopHeadlines>() {
            @Override
            public void onResponse(@NonNull Call<TopHeadlines> call, @NonNull Response<TopHeadlines> response) {
                searchedNewsMutableLiveData.setValue(response.body());
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<TopHeadlines> call, @NonNull Throwable throwable) {
                searchedNewsMutableLiveData.setValue(null);
            }
        });
        return searchedNewsMutableLiveData;
    }


}
