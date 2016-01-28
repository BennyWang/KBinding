package com.benny.app.sample.network.service.caishuo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 12/31/15.
 */
public enum MarketType {
    @SerializedName("美股")
    US,
    @SerializedName("港股")
    HK,
    @SerializedName("沪深")
    SH_SZ,
    UNKNOWN
}