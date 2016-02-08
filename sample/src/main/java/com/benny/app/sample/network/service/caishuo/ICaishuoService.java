package com.benny.app.sample.network.service.caishuo;

import java.util.List;

import retrofit2.http.*;
import rx.Observable;

import com.benny.app.sample.network.service.caishuo.model.Stock;
import com.benny.app.sample.network.service.caishuo.model.User;

/**
 * Created by benny on 9/6/15.
 */

public interface ICaishuoService {
    String API_VERSION = "/api/v1";

    @FormUrlEncoded
    @POST(API_VERSION + "/login")
    Observable<CaishuoEnveloped<User> > login(@Field("email_or_mobile") String identifier, @Field("password") String password);

    @GET(API_VERSION +"/users/{id}/following_stocks")
    Observable<CaishuoEnveloped<List<Stock> > > followedStocks(@Path("id") String id, @Query("per_page") int perPage);

    @GET(API_VERSION +"/stocks/{id}")
    Observable<CaishuoEnveloped<Stock> > stock(@Path("id") String id);
}
