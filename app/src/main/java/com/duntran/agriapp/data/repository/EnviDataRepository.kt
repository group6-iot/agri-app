package com.duntran.agriapp.data.repository

import com.duntran.agriapp.data.api.retrofit.RetrofitHelper
import com.duntran.agriapp.data.model.LoginRequest
import com.duntran.agriapp.data.model.LoginSuccessResponse
import com.duntran.agriapp.ui.main.model.EnvironmentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class EnviDataRepository {
    private var retrofitHelper: RetrofitHelper = RetrofitHelper.getInstance()

    suspend fun getData(key:String): EnvironmentData? {
        return try {
            withContext(Dispatchers.IO) {
                retrofitHelper.remoteService.getEnvironmentData(key)
            }
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
            null
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }
}