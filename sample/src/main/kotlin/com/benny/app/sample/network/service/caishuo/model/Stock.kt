package com.benny.app.sample.network.service.caishuo.model

import com.google.gson.annotations.SerializedName

data class Stock (
    var id: String,
    var symbol: String,
    var name: String,
    @SerializedName("com_name")
    var cnName: String,
    @SerializedName("chi_spelling")
    var chSpelling: String,
    var screenshot: String,
    var followed: Boolean = false,
    var positioned: Boolean = false,

    @SerializedName("comments_count")
    var commentsCount: Int = 0,

    @SerializedName("trading_status")
    var tradingStatus: String,

    @SerializedName("trading_time")
    var tradingTime: String,

    @SerializedName("realtime_price")
    var realtimePrice: Float = 0f,
    @SerializedName("change_percent")
    var changePercent: Float = 0f,
    @SerializedName("change_price")
    var changePrice: Float = 0f,

    var volume: Float = 0f,

    var turnover: Float = 0f,

    var market: MarketType,

    @SerializedName("previous_close")
    var previousClose: Float = 0f,
    var open: Float = 0f,
    var high: Float = 0f,
    var low: Float = 0f,

    @SerializedName("high52_weeks")
    var high52Weeks: Float = 0f,
    @SerializedName("low52_weeks")
    var low52Weeks: Float = 0f,


    @SerializedName("turnover_rate")
    var turnoverRate: Float = 0f,
    @SerializedName("volume_ratio")
    var volumeRatio: Float = 0f,
    @SerializedName("bid_ratio")
    var bidRatio: Float = 0f,


    @SerializedName("exist_reminder")
    var existReminder: Boolean = false,

    @SerializedName("listed_state")
    var listedState: Int = 0
) {
    companion object {
        val LISTED_STATE_ABNORMAL: Int = 0
        val LISTED_STATE_NORMAL: Int = 1
        val LISTED_STATE_DELISTED: Int = 2
        val LISTED_STATE_NOTLISTED: Int = 3

        fun listedStateDescription(listedState: Int): String = when (listedState) {
            Stock.LISTED_STATE_ABNORMAL -> "停牌"
            Stock.LISTED_STATE_NORMAL -> "正常"
            Stock.LISTED_STATE_DELISTED -> "退市";
            Stock.LISTED_STATE_NOTLISTED -> "未上市"
            else -> "未知";
        }
    }
}

val Stock.listedStateDescription: String get() = Stock.listedStateDescription(listedState)
