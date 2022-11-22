package com.duntran.agriapp.data.repository

import com.duntran.agriapp.data.api.retrofit.RetrofitHelper
import com.duntran.agriapp.data.model.*
import com.duntran.agriapp.data.model.devicemode.UpdateDeviceModeRequest
import com.duntran.agriapp.data.model.devicemode.UpdateDeviceModeResponse
import com.duntran.agriapp.data.model.devicestatus.UpdateDeviceStatusRequest
import com.duntran.agriapp.data.model.devicestatus.UpdateDeviceStatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DeviceRepository {
    private var retrofitHelper: RetrofitHelper = RetrofitHelper.getInstance()

    suspend fun changeStatus(device: String, header: String, status: String): UpdateDeviceStatusResponse? {
        return try {
            withContext(Dispatchers.IO) {
                retrofitHelper.remoteService.changeStatus(device, header, UpdateDeviceStatusRequest(status))
            }
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
            null
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    suspend fun findAllDevice(header: String): List<DeviceResponse>? {
        return try {
            withContext(Dispatchers.IO) {
                retrofitHelper.remoteService.findAllDevice(header)
            }
        } catch (ex: UnknownHostException) {
            ex.printStackTrace()
            null
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    suspend fun changeMode(device: String, header: String, mode: UpdateDeviceModeRequest): UpdateDeviceModeResponse? {
        return try {
            withContext(Dispatchers.IO) {
                retrofitHelper.remoteService.changeMode(device, header, mode)
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