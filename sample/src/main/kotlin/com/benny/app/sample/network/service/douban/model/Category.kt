package com.benny.app.sample.network.service.douban.model

/**
 * Created by benny on 1/28/16.
 */

data class Category (
    var count: Int = 0,
    var start: Int = 0,
    var total: Int = 0,
    var title: String,
    var subjects: Array<Movie>
)
