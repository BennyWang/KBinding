package com.benny.app.sample.network.service.douban

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import com.benny.app.sample.network.service.douban.model.Category
import com.benny.app.sample.network.service.douban.model.Movie

/**
 * Created by benny on 1/28/16.
 */

class DoubanService private constructor() {

    internal var service: IDoubanService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        service = retrofit.create(IDoubanService::class.java)
    }

    fun moviesInTheaters(): Observable<Category> {
        return service.movies("in_theaters", API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun moviesComingSoon(): Observable<Category> {
        return service.movies("coming_soon", API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun moviesTop250(): Observable<Category> {
        return service.movies("top250", API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun movie(id: String): Observable<Movie> {
        return service.movie(id, API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        val API_KEY = "00aefce4d06e0bb7020cf6ae714a2325"
        val API_BASE_URL = "https://api.douban.com"

        val instance: DoubanService by lazy { DoubanService() }
    }
}
