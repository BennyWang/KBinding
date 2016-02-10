package com.benny.app.sample.network.service.caishuo

import com.benny.app.sample.network.service.caishuo.model.CaishuoEnveloped
import retrofit2.http.*
import rx.Observable

import com.benny.app.sample.network.service.caishuo.model.Stock
import com.benny.app.sample.network.service.caishuo.model.User

/**
 * Created by benny on 9/6/15.
 */

interface ICaishuoService {

    @FormUrlEncoded
    @POST(API_VERSION + "/login")
    fun login(@Field("email_or_mobile") identifier: String, @Field("password") password: String): Observable<CaishuoEnveloped<User>>

    @GET(API_VERSION + "/users/{id}/following_stocks")
    fun followedStocks(@Path("id") id: String, @Query("per_page") perPage: Int): Observable<CaishuoEnveloped<List<Stock>>>

    @GET(API_VERSION + "/stocks/{id}")
    fun stock(@Path("id") id: String): Observable<CaishuoEnveloped<Stock>>

    companion object {
        const val API_VERSION = "/api/v1"
    }
}
