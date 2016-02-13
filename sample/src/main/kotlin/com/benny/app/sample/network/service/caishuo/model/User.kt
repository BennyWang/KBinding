package com.benny.app.sample.network.service.caishuo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by benny on 9/5/15.
 */

data class User (
    var id: String,
    var name: String,
    var photo: String,
    var email: String,
    var mobile: String,
    var gender: String,
    var province: String,
    var city: String,

    @SerializedName("access_token")
    var accessToken: String
)
