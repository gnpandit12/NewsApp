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

    private final HeadlinesRepository headlinesRepository;

    public TopHeadlinesViewModel(@NonNull Application application) {
        super(application);
        headlinesRepository = HeadlinesRepository.getInstance();
    }

    public MutableLiveData<TopHeadlines> getTopHeadlines(
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
/*
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
*/


    public MutableLiveData<Boolean> getIsLoading() {
        return headlinesRepository.getIsLoading();
    }

/*
    private MutableLiveData<Boolean> getIsLoadingResponse() {
        return headlinesRepository.getIsLoading();
    }
*/

    public MutableLiveData<TopHeadlines> getSearchedNews(
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


/*
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
*/

}
