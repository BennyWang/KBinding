package com.benny.app.sample.network.service.caishuo;

import com.benny.app.sample.ApplicationContext;

import retrofit.RequestInterceptor;

/**
 * Created by benny on 9/6/15.
 */
public class InsertHeadersRequestInterceptor implements RequestInterceptor {
    private String apiKey;

    public InsertHeadersRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("X-Client-Key", apiKey);
        request.addHeader("X-SN-Code", ApplicationContext.INSTANCE.getDeviceId());
        request.addHeader("X-Client-Version", ApplicationContext.INSTANCE.getVersion());
        if(ApplicationContext.INSTANCE.getHasLogin()) {
            request.addHeader("Authorization", ApplicationContext.INSTANCE.getAccessToken());
        }
    }
}
