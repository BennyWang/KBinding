package com.benny.app.sample.network.service.caishuo.model

/**
 * Created by benny on 9/5/15.
 */
data class CaishuoEnveloped<T>(
    var status: Int = 0,
    var error: Error,
    var data: T
)


val CaishuoEnveloped<*>.isSuccess: Boolean get() = status == 1