package com.benny.app.sample.network.service.douban.model

/**
 * Created by benny on 1/28/16.
 */

class Rating(
    var max: Int = 0,
    var min: Int = 0,

    var average: Float = 0.toFloat(),
    var starts: String
)