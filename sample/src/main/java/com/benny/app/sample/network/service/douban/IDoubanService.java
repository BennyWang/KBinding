package com.benny.app.sample.network.service.douban;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import com.benny.app.sample.network.service.douban.model.Category;
import com.benny.app.sample.network.service.douban.model.Movie;

/**
 * Created by benny on 1/28/16.
 */

public interface IDoubanService {
    String API_VERSION = "/v2";

    @GET(API_VERSION +"/movie/{type}")
    Observable<Category> movies(@Path("type") String type, @Query("apikey") String key);

    @GET(API_VERSION +"/movie/subject/{id}")
    Observable<Movie> movie(@Path("id") String id, @Query("apikey") String key);
}
