package com.benny.app.sample.network.service.douban

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

import com.benny.app.sample.network.service.douban.model.Category
import com.benny.app.sample.network.service.douban.model.Movie

/**
 * Created by benny on 1/28/16.
 */

interface IDoubanService {

    @GET(API_VERSION + "/movie/{type}")
    fun movies(@Path("type") type: String, @Query("apikey") key: String): Observable<Category>

    @GET(API_VERSION + "/movie/subject/{id}")
    fun movie(@Path("id") id: String, @Query("apikey") key: String): Observable<Movie>

    companion object {
        const val API_VERSION = "/v2"
    }
}
