package com.benny.app.sample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 9/5/15.
 */
public class Error {
    public final static int NETWORK_ERROR = 500;
    public final static int SYSTEM_ERROR = 2001;

    public int code;

    @SerializedName("msg")
    public String message;

    public Error() {
    }

    public Error(int code) {
        this.code = code;
    }
}
