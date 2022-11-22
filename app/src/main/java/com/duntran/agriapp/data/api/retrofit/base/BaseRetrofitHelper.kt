package com.duntran.agriapp.data.api.retrofit.base

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class BaseRetrofitHelper {
    protected var okHttpClient: OkHttpClient? = null
    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .writeTimeout(10 * 1000L, TimeUnit.MILLISECONDS)
            .readTimeout(10 * 1000L, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
        okHttpClient = builder.build()
    }

}