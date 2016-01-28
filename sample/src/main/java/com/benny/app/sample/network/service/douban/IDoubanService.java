package com.benny.app.sample.network.service.douban;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

import com.benny.app.sample.network.service.douban.model.Category;
import com.benny.app.sample.network.service.douban.model.Movie;

/**
 * Created by benny on 1/28/16.
 */
public interface IDoubanService {
    String API_VERSION = "/v2";

    @GET(API_VERSION +"/movie/{type}")
    Observable<Category> movies(@Query("apikey") String key, @Path("type") String type);

    @GET(API_VERSION +"/movie/subject/{id}")
    Observable<Movie> movie(@Query("apikey") String key, @Path("id") String id);
}
