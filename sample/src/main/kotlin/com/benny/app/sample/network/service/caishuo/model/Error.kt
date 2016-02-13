package com.benny.app.sample.network.service.caishuo.model

import com.google.gson.annotations.SerializedName

/**
 * Created by benny on 9/5/15.
 */

data class Error (var code: Int = 0, @SerializedName("msg") var message: String)
