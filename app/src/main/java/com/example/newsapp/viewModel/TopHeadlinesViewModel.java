package com.example.newsapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.newsapp.model.data.TopHeadlines;
import com.example.newsapp.model.repository.HeadlinesRepository;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class TopHeadlinesViewModel extends AndroidViewModel {

    private HeadlinesRepository headlinesRepository;
    private MutableLiveData<TopHeadlines> topHeadlinesMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<TopHeadlines> searchedNewsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public TopHeadlinesViewModel(@NonNull Application application) {
        super(application);
        headlinesRepository = new HeadlinesRepository();
    }

    public MutableLiveData<TopHeadlines> getTopHeadlines(
            String category,
            String language,
            String country,
            String maxHeadlines,
            String apiKey
    ) {
        topHeadlinesMutableLiveData = getTopHeadlinesMutableLiveData(
                category,
                language,
                country,
                maxHeadlines,
                apiKey);
        return topHeadlinesMutableLiveData;
    }
    private MutableLiveData<TopHeadlines> getTopHeadlinesMutableLiveData(
            String category,
            String language,
            String country,
            String maxHeadlines,
            String apiKey
    ) {
        return headlinesRepository.getTopHeadlinesResponse(
                category,
                language,
                country,
                maxHeadlines,
                apiKey
        );
    }


    public MutableLiveData<Boolean> getIsLoading() {
        isLoading = getIsLoadingResponse();
        return isLoading;
    }
    private MutableLiveData<Boolean> getIsLoadingResponse() {
        return headlinesRepository.getIsLoading();
    }

    public MutableLiveData<TopHeadlines> getSearchedNews(
            String searchKeyword,
            String lang,
            String country,
            String max,
            String apikey
    ) {
        searchedNewsMutableLiveData = getSearchedNewsMutableLiveData(
                searchKeyword,
                lang,
                country,
                max,
                apikey);
        return searchedNewsMutableLiveData;
    }
    private MutableLiveData<TopHeadlines> getSearchedNewsMutableLiveData(
            String searchKeyword,
            String lang,
            String country,
            String max,
            String apikey
    ) {
        return headlinesRepository.getSearchedNewsResponse(
                searchKeyword,
                lang,
                country,
                max,
                apikey
        );
    }
}
