package com.benny.app.sample.network.service.caishuo

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Response

import com.benny.app.sample.ApplicationContext

/**
 * Created by benny on 9/6/15.
 */

class InsertHeadersInterceptor(private val apiKey: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("X-Client-Key", apiKey)
        requestBuilder.addHeader("X-SN-Code", ApplicationContext.deviceId)
        requestBuilder.addHeader("X-Client-Version", ApplicationContext.version)
        return chain.proceed(requestBuilder.build())
    }
}
