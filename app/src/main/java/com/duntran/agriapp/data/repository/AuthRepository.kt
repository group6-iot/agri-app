package com.duntran.agriapp.data.repository

import com.duntran.agriapp.data.api.retrofit.RetrofitHelper
import com.duntran.agriapp.data.model.LoginRequest
import com.duntran.agriapp.data.model.LoginSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class AuthRepository {
    private var retrofitHelper: RetrofitHelper = RetrofitHelper.getInstance()

    suspend fun login(username: String, password:String): LoginSuccessResponse? {
        return try {
            withContext(Dispatchers.IO) {
                retrofitHelper.remoteService.login(LoginRequest(username, password))
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