package com.benny.app.sample.network.service.caishuo;

import com.benny.app.sample.model.Error;
import com.google.gson.JsonElement;

/**
 * Created by benny on 9/5/15.
 */
public class CaishuoEnveloped<T> {
    public int status;
    public Error error;
    public T data;

    @Override
    public String toString() {
        return "Status: " + status + " data: " + data.toString();
    }

    public boolean isSuccess(){
        return status == 1;
    }
}
