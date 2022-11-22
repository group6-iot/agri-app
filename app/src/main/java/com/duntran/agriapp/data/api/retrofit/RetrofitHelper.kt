package com.duntran.agriapp.data.api.retrofit

import com.duntran.agriapp.data.api.retrofit.base.BaseRetrofitHelper
import com.duntran.agriapp.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper : BaseRetrofitHelper() {
    var remoteService: RemoteService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient!!).build()

        remoteService = retrofit.create(RemoteService::class.java)
    }

    companion object {
        fun getInstance(): RetrofitHelper {
            return RetrofitHelper()
        }
    }
}