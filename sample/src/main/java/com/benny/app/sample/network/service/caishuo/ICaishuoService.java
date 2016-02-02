package com.benny.app.sample.network.service.caishuo;

import com.benny.app.sample.network.service.caishuo.model.Stock;
import com.benny.app.sample.network.service.caishuo.model.User;
import java.util.List;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by benny on 9/6/15.
 */

public interface ICaishuoService {
    String API_VERSION = "/api/v1";

    @FormUrlEncoded
    @POST(API_VERSION + "/login")
    Observable<User> login(@Field("email_or_mobile") String identifier, @Field("password") String password);

    @GET(API_VERSION +"/users/{id}/following_stocks")
    Observable<List<Stock>> followedStocks(@Path("id") String id, @Query("per_page") int perPage);

    @GET(API_VERSION +"/stocks/{id}")
    Observable<Stock> stock(@Path("id") String id);
}
