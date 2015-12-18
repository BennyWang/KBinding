package com.benny.app.sample.network.service;

/**
 * Created by benny on 9/6/15.
 */
public class ServiceException extends RuntimeException {
    int errorCode;

    public ServiceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
