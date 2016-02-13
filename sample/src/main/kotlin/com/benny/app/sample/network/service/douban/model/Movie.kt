package com.benny.app.sample.network.service.douban.model

import com.google.gson.annotations.SerializedName

/**
 * Created by benny on 1/28/16.
 */
data class Movie (
    var id: String,
    var title: String,

    var rating: Rating,

    @SerializedName("original_title")
    var originalTitle: String,

    var subtype: String,
    var summary: String,

    @SerializedName("reviews_count")
    var reviewsCount: Int = 0,

    @SerializedName("wish_count")
    var wishCount: Int = 0,

    @SerializedName("comments_count")
    var commentsCount: Int = 0,

    @SerializedName("ratings_count")
    var ratingsCount: Int = 0,

    @SerializedName("douban_site")
    var doubanSite: String,

    var year: String,
    var images: Avatars,
    var alt: String,

    @SerializedName("mobile_url")
    var mobileUrl: String,

    @SerializedName("share_url")
    var shareUrl: String,

    @SerializedName("schedule_url")
    var scheduleUrl: String,

    var genres: Array<String>,

    var countries: Array<String>,

    var casts: Array<Cast>,

    var directors: Array<Cast>,

    var aka: Array<String>
)