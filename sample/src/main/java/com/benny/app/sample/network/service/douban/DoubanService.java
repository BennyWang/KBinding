package com.benny.app.sample.network.service.douban;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.benny.app.sample.network.service.douban.model.Category;
import com.benny.app.sample.network.service.douban.model.Movie;

/**
 * Created by benny on 1/28/16.
 */

public class DoubanService {
    public static final String API_KEY = "00aefce4d06e0bb7020cf6ae714a2325";
    public static final String API_BASE_URL = "https://api.douban.com";

    private static DoubanService instance;

    IDoubanService service;

    public static DoubanService getInstance() {
        if(instance == null) instance = new DoubanService();
        return instance;
    }

    private DoubanService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(IDoubanService.class);
    }

    public Observable<Category> moviesInTheaters() {
        return service.movies("in_theaters", API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Category> moviesComingSoon() {
        return service.movies("coming_soon", API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Category> moviesTop250() {
        return service.movies("top250", API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Movie> movie(String id) {
        return service.movie(id, API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
