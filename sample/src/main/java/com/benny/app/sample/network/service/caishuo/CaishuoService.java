package com.benny.app.sample.network.service.caishuo;

import com.benny.app.sample.Constants;
import com.benny.app.sample.network.service.ErrorHandler;
import com.benny.app.sample.network.service.caishuo.model.Stock;
import com.benny.app.sample.network.service.caishuo.model.User;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit.RestAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(API_BASE)
                .setRequestInterceptor(new InsertHeadersRequestInterceptor(API_KEY))
                .setConverter(new CaishuoGsonCoverter(new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create()))
                .setErrorHandler(new ErrorHandler())
                .build();
        service = retrofit.create(ICaishuoService.class);
    }

    public Observable<User> login(final String identifier, final String password) {
        return service.login(identifier, password).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Stock>> followedStocks(final String id) {
        return service.followedStocks(id, 50).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Stock> stock(final String id) {
        return service.stock(id).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
