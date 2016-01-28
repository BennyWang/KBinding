package com.benny.app.sample.network.service.douban.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by benny on 1/28/16.
 */
public class Movie {
    public String id;
    public String title;

    public Rating rating;

    @SerializedName("original_title")
    public String originalTitle;

    public String subtype;
    public String summary;

    @SerializedName("reviews_count")
    public int reviewsCount;

    @SerializedName("wish_count")
    public int wishCount;

    @SerializedName("comments_count")
    public int commentsCount;

    @SerializedName("ratings_count")
    public int ratingsCount;

    @SerializedName("douban_site")
    public String doubanSite;

    public String year;
    public Avatars images;
    public String alt;

    @SerializedName("mobile_url")
    public String mobileUrl;

    @SerializedName("share_url")
    public String shareUrl;

    @SerializedName("schedule_url")
    public String scheduleUrl;

    public String[] genres;

    public String[] countries;

    public Cast[] casts;

    public Cast[] directors;

    public String[] aka;
}
