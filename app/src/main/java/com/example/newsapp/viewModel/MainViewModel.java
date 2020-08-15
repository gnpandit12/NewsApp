package com.example.newsapp.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.model.NewsAPIService;
import com.example.newsapp.model.di.ApiComponent;
import com.example.newsapp.model.di.ApiModule;
import com.example.newsapp.model.di.DaggerApiComponent;
import com.example.newsapp.model.pojoclass.NewsAPIResponse;

import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends ViewModel {


    private static final String TAG = "main_view_model";
    private ApiComponent component;

    @Inject
    NewsAPIService newsAPIService;
    public MutableLiveData<NewsAPIResponse> newsAPIResponseMutableLiveData =
            new MutableLiveData<>();
    public MutableLiveData<Boolean> newsResponseLoadingErrorMutableLiveData =
            new MutableLiveData<>();
    public  MutableLiveData<Boolean> newsLoadingMutableLiveData =
            new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MainViewModel(){
        component = DaggerApiComponent.builder().apiModule(new ApiModule()).build();
        component.inject(this);
    }


    public void getCountrySpecificNews(
            String country,
            String category,
            String apiKey
    ) {
        newsLoadingMutableLiveData.setValue(true);
        newsResponseLoadingErrorMutableLiveData.setValue(false);

        if ("India".equals(country)){
            country = "in";
        }else if ("United States".equals(country)){
            country = "us";
        }


        compositeDisposable.add(
                newsAPIService.getCountrySpecificNews(country, category, apiKey)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<NewsAPIResponse>() {
                    @Override
                    public void onSuccess(NewsAPIResponse newsAPIResponse) {
                        Log.d(TAG, "onSuccess: ");
                        newsLoadingMutableLiveData.setValue(false);
                        if (newsAPIResponse != null){
                            newsAPIResponseMutableLiveData.setValue(newsAPIResponse);
                        }else {
                            newsResponseLoadingErrorMutableLiveData.setValue(true);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.toString());
                        newsLoadingMutableLiveData.setValue(false);
                        newsResponseLoadingErrorMutableLiveData.setValue(true);
                    }
                })
        );

    }


}
