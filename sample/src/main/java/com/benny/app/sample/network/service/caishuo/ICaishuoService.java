package com.benny.app.sample.network.service.caishuo;

import com.benny.app.sample.model.Stock;
import retrofit.http.*;

import com.benny.app.sample.model.User;
import rx.Observable;

import java.util.List;

/**
 * Created by benny on 9/6/15.
 */

public interface ICaishuoService {
    String API_VERSION = "/api/v1";

    @FormUrlEncoded
    @POST(API_VERSION + "/login")
    Observable<User> login(@Field("email_or_mobile") String identifier, @Field("password") String password);

    @GET(API_VERSION +"/users/{id}/following_stocks")
    Observable<List<Stock>> followedStocks(@Path("id") String id);
}
