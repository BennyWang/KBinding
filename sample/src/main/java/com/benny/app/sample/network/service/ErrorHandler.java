package com.benny.app.sample.network.service;

import com.benny.app.sample.Constants;
import retrofit.RetrofitError;

/**
 * Created by benny on 9/9/15.
 */
public class ErrorHandler implements retrofit.ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        if(cause.getCause() instanceof ServiceException) {
            return cause.getCause();
        }

        ServiceException throwable = new ServiceException(Constants.MESSAGE_NETWORK_ERROR, Constants.CODE_NETWORK_ERROR);
        if(cause.getResponse() != null) {
            throwable = new ServiceException(Constants.MESSAGE_SERVER_ERROR, Constants.CODE_SERVER_ERROR);
        }

        return throwable;
    }
}
