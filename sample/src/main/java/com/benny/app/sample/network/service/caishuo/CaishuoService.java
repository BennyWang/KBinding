package com.benny.app.sample.network.service.caishuo;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import com.benny.app.sample.network.service.caishuo.model.Stock;
import com.benny.app.sample.network.service.caishuo.model.User;

/**
 * Created by benny on 9/5/15.
 */

public class CaishuoService {

    private static final String BASE_TESTING    = "http://testing.caishuo.com";
    private static final String BASE_H5_TESTING = "http://m.testing.caishuo.com";

    private static final String BASE_OFFICE     = "https://office.caishuo.com";
    private static final String BASE_H5_OFFICE  = "http://m.office.caishuo.com";

    private static final String BASE_PROD       = "https://www.caishuo.com";
    private static final String BASE_H5_PROD    = "https://m.caishuo.com";

    private static final String API_KEY_PROD    = "adasdasdasdasdasdasdasdasdasdasdasd";
    private static final String API_KEY_DEV     = "123456";

    private static final String API_BASE = BASE_TESTING;
    private static final String H5_BASE = BASE_H5_TESTING;
    private static final String API_KEY = API_KEY_DEV;

    private static CaishuoService instance;

    ICaishuoService service;

    public static CaishuoService getInstance() {
        if(instance == null) instance = new CaishuoService();
        return instance;
    }

    private CaishuoService() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new InsertHeadersInterceptor(API_KEY_DEV));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_TESTING)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(clientBuilder.build())
                .build();

        service = retrofit.create(ICaishuoService.class);
    }

    public Observable<User> login(final String identifier, final String password) {
        return service.login(identifier, password).map(new Func1<CaishuoEnveloped<User>, User>() {
            @Override
            public User call(CaishuoEnveloped<User> userCaishuoEnveloped) {
                return userCaishuoEnveloped.data;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Stock>> followedStocks(final String id) {
        return service.followedStocks(id, 50).map(new Func1<CaishuoEnveloped<List<Stock>>, List<Stock>>() {
            @Override
            public List<Stock> call(CaishuoEnveloped<List<Stock>> caishuoEnveloped) {
                return caishuoEnveloped.data;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Stock> stock(final String id) {
        return service.stock(id).map(new Func1<CaishuoEnveloped<Stock>, Stock>() {
            @Override
            public Stock call(CaishuoEnveloped<Stock> stockCaishuoEnveloped) {
                return stockCaishuoEnveloped.data;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
