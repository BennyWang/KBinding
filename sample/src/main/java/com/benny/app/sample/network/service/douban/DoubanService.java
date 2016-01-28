package com.benny.app.sample.network.service.douban;

import com.benny.app.sample.Constants;
import com.benny.app.sample.network.service.ErrorHandler;
import com.benny.app.sample.network.service.douban.model.Category;
import com.benny.app.sample.network.service.douban.model.Movie;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(API_BASE_URL)
                .setConverter(new GsonConverter(new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create()))
                .setErrorHandler(new ErrorHandler())
                .build();
        service = retrofit.create(IDoubanService.class);
    }

    public Observable<Category> moviesInTheaters() {
        return service.movies(API_KEY, "in_theaters").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Category> moviesComingSoon() {
        return service.movies(API_KEY, "coming_soon").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Category> moviesTop250() {
        return service.movies(API_KEY, "top250").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Movie> movie(@Path("id") String id) {
        return service.movie(API_KEY, id).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
