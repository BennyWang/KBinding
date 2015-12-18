package com.benny.app.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Stock implements Parcelable {

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

    @SerializedName("funds_flow")
    public FundFlow[] fundsFlow = new FundFlow[0];

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

    public Stock() {}

    protected Stock(Parcel in) {
        id = in.readString();
        symbol = in.readString();
        name = in.readString();
        cnName = in.readString();
        chSpelling = in.readString();
        screenshot = in.readString();
        followed = in.readByte() != 0;
        positioned = in.readByte() != 0;
        commentsCount = in.readInt();
        tradingStatus = in.readString();
        tradingTime = in.readString();
        realtimePrice = in.readFloat();
        changePercent = in.readFloat();
        changePrice = in.readFloat();
        amplitude = in.readFloat();
        nonRestrictedMarketCapitalization = in.readFloat();
        netAssetRate = in.readFloat();
        marketCapitalization = in.readFloat();
        peRatio = in.readFloat();
        volume = in.readFloat();
        turnover = in.readFloat();
        previousClose = in.readFloat();
        open = in.readFloat();
        high = in.readFloat();
        low = in.readFloat();
        upPrice = in.readFloat();
        downPrice = in.readFloat();
        high52Weeks = in.readFloat();
        low52Weeks = in.readFloat();
        turnoverRate = in.readFloat();
        volumeRatio = in.readFloat();
        bidRatio = in.readFloat();
        existReminder = in.readByte() != 0;
        listedState = in.readInt();
        currencyUnit = in.readString();
        flowCharts = in.createFloatArray();
        boardLot = in.readInt();
        isIndex = in.readByte() != 0;
        eps = in.readFloat();
        dividend = in.readFloat();
        singlePosition = in.readFloat();
        exchange = in.readString();
        marketValue = in.readString();
        industry = in.readString();
        weight = in.readFloat();
        averageDailyVolume = in.readString();
        outcome = in.readString();
        message = in.readString();
        date = in.readString();
        time = in.readString();
        last = in.readFloat();
        changeFromPreviousClose = in.readFloat();
        percentChangeFromPreviousClose = in.readFloat();
        bidPrices = in.readString();
        offerPrices = in.readString();
        tradeTime = in.readString();
        utfOffset = in.readString();
        bidSizes = in.readString();
        offerSizes = in.readString();
        contest = in.readInt();
        isBasket = in.readByte() != 0;
        title = in.readString();
        totalrevenue = in.readFloat();
        realtimeIndex = in.readFloat();
        totalChange = in.readFloat();
        totalChangePercent = in.readFloat();
        count = in.readInt();
        availableCount = in.readInt();
        cost = in.readFloat();
        averageCost = in.readFloat();
        totalValue = in.readFloat();
        stockscore = in.readInt();
        todayChange = in.readFloat();
        todayChangePercent = in.readFloat();
        broker = in.readString();
        originalId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(symbol);
        dest.writeString(name);
        dest.writeString(cnName);
        dest.writeString(chSpelling);
        dest.writeString(screenshot);
        dest.writeByte((byte) (followed ? 1 : 0));
        dest.writeByte((byte) (positioned ? 1 : 0));
        dest.writeInt(commentsCount);
        dest.writeString(tradingStatus);
        dest.writeString(tradingTime);
        dest.writeFloat(realtimePrice);
        dest.writeFloat(changePercent);
        dest.writeFloat(changePrice);
        dest.writeFloat(amplitude);
        dest.writeFloat(nonRestrictedMarketCapitalization);
        dest.writeFloat(netAssetRate);
        dest.writeFloat(marketCapitalization);
        dest.writeFloat(peRatio);
        dest.writeFloat(volume);
        dest.writeFloat(turnover);
        dest.writeFloat(previousClose);
        dest.writeFloat(open);
        dest.writeFloat(high);
        dest.writeFloat(low);
        dest.writeFloat(upPrice);
        dest.writeFloat(downPrice);
        dest.writeFloat(high52Weeks);
        dest.writeFloat(low52Weeks);
        dest.writeFloat(turnoverRate);
        dest.writeFloat(volumeRatio);
        dest.writeFloat(bidRatio);
        dest.writeByte((byte) (existReminder ? 1 : 0));
        dest.writeInt(listedState);
        dest.writeString(currencyUnit);
        dest.writeFloatArray(flowCharts);
        dest.writeInt(boardLot);
        dest.writeByte((byte) (isIndex ? 1 : 0));
        dest.writeFloat(eps);
        dest.writeFloat(dividend);
        dest.writeFloat(singlePosition);
        dest.writeString(exchange);
        dest.writeString(marketValue);
        dest.writeString(industry);
        dest.writeFloat(weight);
        dest.writeString(averageDailyVolume);
        dest.writeString(outcome);
        dest.writeString(message);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeFloat(last);
        dest.writeFloat(changeFromPreviousClose);
        dest.writeFloat(percentChangeFromPreviousClose);
        dest.writeString(bidPrices);
        dest.writeString(offerPrices);
        dest.writeString(tradeTime);
        dest.writeString(utfOffset);
        dest.writeString(bidSizes);
        dest.writeString(offerSizes);
        dest.writeInt(contest);
        dest.writeByte((byte) (isBasket ? 1 : 0));
        dest.writeString(title);
        dest.writeFloat(totalrevenue);
        dest.writeFloat(realtimeIndex);
        dest.writeFloat(totalChange);
        dest.writeFloat(totalChangePercent);
        dest.writeInt(count);
        dest.writeInt(availableCount);
        dest.writeFloat(cost);
        dest.writeFloat(averageCost);
        dest.writeFloat(totalValue);
        dest.writeInt(stockscore);
        dest.writeFloat(todayChange);
        dest.writeFloat(todayChangePercent);
        dest.writeString(broker);
        dest.writeString(originalId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    public float getRealtimePrice() {
        return isBasket ? realtimeIndex : realtimePrice;
    }

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
