package com.benny.app.sample.network.service.caishuo


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.app.sample.network.service.caishuo.model.User

/**
 * Created by benny on 9/5/15.
 */

class CaishuoService private constructor() {

    internal var service: ICaishuoService

    init {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(InsertHeadersInterceptor(API_KEY_DEV))

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_TESTING)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(clientBuilder.build())
                .build()

        service = retrofit.create(ICaishuoService::class.java)
    }

    fun login(identifier: String, password: String): Observable<User> {
        return service.login(identifier, password).map { it.data }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun followedStocks(id: String): Observable<List<Stock>> {
        return service.followedStocks(id, 50).map { it.data }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    fun stock(id: String): Observable<Stock> {
        return service.stock(id).map { it.data }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    companion object {

        private val BASE_TESTING = "http://testing.caishuo.com"
        private val BASE_H5_TESTING = "http://m.testing.caishuo.com"

        private val BASE_OFFICE = "https://office.caishuo.com"
        private val BASE_H5_OFFICE = "http://m.office.caishuo.com"

        private val BASE_PROD = "https://www.caishuo.com"
        private val BASE_H5_PROD = "https://m.caishuo.com"

        private val API_KEY_PROD = "adasdasdasdasdasdasdasdasdasdasdasd"
        private val API_KEY_DEV = "123456"

        private val API_BASE = BASE_TESTING
        private val H5_BASE = BASE_H5_TESTING
        private val API_KEY = API_KEY_DEV

        val instance: CaishuoService by lazy { CaishuoService() }
    }
}
