package com.benny.app.sample.network.service.caishuo.model;

import com.google.gson.annotations.SerializedName;

public class Stock {

    public String id;
    public String symbol;
    public String name;
    @SerializedName("com_name")
    public String cnName;
    @SerializedName("chi_spelling")
    public String chSpelling;
    public String screenshot;
    public boolean followed;
    public boolean positioned;

    @SerializedName("comments_count")
    public int commentsCount;

    @SerializedName("trading_status")
    public String tradingStatus;

    @SerializedName("trading_time")
    public String tradingTime;

    @SerializedName("realtime_price")
    public float realtimePrice;
    @SerializedName("change_percent")
    public float changePercent;
    @SerializedName("change_price")
    public float changePrice;

    public float[][] bids;
    public float[][] offers;

    @SerializedName("rt_logs")
    public String[][] rtLogs;

    public float amplitude;
    @SerializedName("non_restricted_market_capitalization")
    public float nonRestrictedMarketCapitalization;
    @SerializedName("net_asset_rate")
    public float netAssetRate;
    @SerializedName("market_capitalization")
    public float marketCapitalization;
    @SerializedName("pe_ratio")
    public float peRatio;

    public float volume;

    public float turnover;

    public MarketType market;

    @SerializedName("previous_close")
    public float previousClose;
    public float open;
    public float high;
    public float low;
    @SerializedName("up_price")
    public float upPrice;
    @SerializedName("down_price")
    public float downPrice;
    @SerializedName("high52_weeks")
    public float high52Weeks;
    @SerializedName("low52_weeks")
    public float low52Weeks;


    @SerializedName("turnover_rate")
    public float turnoverRate;
    @SerializedName("volume_ratio")
    public float volumeRatio;
    @SerializedName("bid_ratio")
    public float bidRatio;


    @SerializedName("exist_reminder")
    public boolean existReminder;

    @SerializedName("listed_state")
    public int listedState;

    public Position[] position;

    @SerializedName("my_friends_followed_stock")
    public User[] stockFollowFriends;
    @SerializedName("my_friends_positioned_stock")
    public User[] stockPositionFriends;


    @SerializedName("current_currency_unit")
    public String currencyUnit;

    @SerializedName("flow_charts")
    public float[] flowCharts;

    @SerializedName("board_lot")
    public int boardLot;


    @SerializedName("is_index")
    public boolean isIndex;

    public float eps = -1;

    public float dividend = -1;
    //---------------------------------------------------------------------------

    @SerializedName("single_position")
    public float singlePosition;

    public String exchange;
    @SerializedName("market_value")
    public String marketValue;
    public String industry;


    public float weight;

    @SerializedName("average_daily_volume")
    public String averageDailyVolume;

    public String outcome;
    public String message;
    public String date;
    public String time;


    public float last;

    @SerializedName("change_from_previous_close")
    public float changeFromPreviousClose;
    @SerializedName("percent_change_from_previous_close")
    public float percentChangeFromPreviousClose;
    @SerializedName("bid_prices")
    public String bidPrices;
    @SerializedName("offer_prices")
    public String offerPrices;

    @SerializedName("trade_time")
    public String tradeTime;
    @SerializedName("utc_offset")
    public String utfOffset;
    @SerializedName("bid_sizes")
    public String bidSizes;
    @SerializedName("offer_sizes")
    public String offerSizes;
    public int contest;

    public enum Action {
        @SerializedName("0")
        UnChanged,
        @SerializedName("1")
        New,
        @SerializedName("2")
        Delete,
        @SerializedName("3")
        Increase,
        @SerializedName("4")
        Decrease,
        Unknown;

        @Override
        public String toString() {
            switch (this) {
                case UnChanged:
                    return "维持";
                case New:
                    return "建仓";
                case Delete:
                    return "平仓";
                case Increase:
                    return "加仓";
                case Decrease:
                    return "减仓";
                default:
                    return "未知";
            }
        }
    }

    public Action action;

    public static final int LISTED_STATE_ABNORMAL = 0;
    public static final int LISTED_STATE_NORMAL = 1;
    public static final int LISTED_STATE_DELISTED = 2;
    public static final int LISTED_STATE_NOTLISTED = 3;

    public String getListedStateDescription() {
        switch (listedState) {
            case LISTED_STATE_ABNORMAL:
                return "停牌";
            case LISTED_STATE_NORMAL:
                return "正常";
            case LISTED_STATE_DELISTED:
                return "退市";
            case LISTED_STATE_NOTLISTED:
                return "未上市";
            default:
                return "未知";
        }
    }

    public static String getListedStateDescription(int listedState) {
        switch (listedState) {
            case LISTED_STATE_ABNORMAL:
                return "停牌";
            case LISTED_STATE_NORMAL:
                return "正常";
            case LISTED_STATE_DELISTED:
                return "退市";
            case LISTED_STATE_NOTLISTED:
                return "未上市";
            default:
                return "未知";
        }
    }

    //---------------------------------------------------------------------------------

    public boolean isBasket;
    public String title;
    @SerializedName("return_from_start_on")
    public float totalrevenue;
    @SerializedName("realtime_index")
    public float realtimeIndex;

    //------------------------------
    @SerializedName("total_change")
    public float totalChange;

    @SerializedName("total_change_percent")
    public float totalChangePercent;
    @SerializedName("total_count")
    public int count;
    @SerializedName("available_count")
    public int availableCount;

    @SerializedName("total_cost")
    public float cost;
    @SerializedName("average_cost")
    public float averageCost;
    @SerializedName("total_value")
    public float totalValue;

    @SerializedName("stock_score")
    public int stockscore;
    @SerializedName("today_change")
    public float todayChange;


    @SerializedName("today_change_percent")
    public float todayChangePercent;
    public String broker;
    @SerializedName("original_id")
    public String originalId;

    @Override
    public String toString() {
        return "[id]" + id + " " + name;
    }

    public class Position {
        public float shares;

        @SerializedName("avg_cost")
        public float avgCost;

        @SerializedName("market_value")
        public String marketValue;

        @SerializedName("total_cost")
        public float totalCost;

        @SerializedName("current_change")
        public float currentChange;

        @SerializedName("current_change_percent")
        public float currentChangePercent;

        @SerializedName("total_change")
        public float totalChange;

        @SerializedName("total_change_percent")
        public float totalChangePercent;

        @SerializedName("broker_name")
        public String brokerName;

        @SerializedName("broker_no")
        public String brokerNo;

        @SerializedName("account_id")
        public String accountId;
    }

    public class FundFlow {
        public String date;
        public long value;
    }

}
