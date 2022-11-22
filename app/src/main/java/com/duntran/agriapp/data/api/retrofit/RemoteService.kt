package com.duntran.agriapp.data.api.retrofit

import com.duntran.agriapp.data.model.*
import com.duntran.agriapp.data.model.devicemode.UpdateDeviceModeRequest
import com.duntran.agriapp.data.model.devicemode.UpdateDeviceModeResponse
import com.duntran.agriapp.data.model.devicestatus.UpdateDeviceStatusRequest
import com.duntran.agriapp.data.model.devicestatus.UpdateDeviceStatusResponse
import com.duntran.agriapp.ui.main.model.EnvironmentData
import com.duntran.agriapp.utils.Constants.LOGIN_URL
import retrofit2.http.*

interface RemoteService {

//    @GET("products")
//    suspend fun getAllProduct(): List<ProductDTO>
//
//    @GET("colors")
//    suspend fun getAllColor(): List<Color>
//
//    @FormUrlEncoded
    @Headers("Content-Type: application/vnd.api+json")
    @POST(LOGIN_URL)
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginSuccessResponse

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("/devices/{device}/status")
    suspend fun changeStatus(
        @Path(value = "device", encoded = true) device: String,
        @Header("x-api-key") header: String,
        @Body updateDeviceStatusRequest: UpdateDeviceStatusRequest
    ): UpdateDeviceStatusResponse

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("/devices/{device}/mode")
    suspend fun changeMode(
        @Path(value = "device", encoded = true) device: String,
        @Header("x-api-key") header: String,
        @Body updateDeviceModeRequest: UpdateDeviceModeRequest
    ): UpdateDeviceModeResponse


    @GET("/devices")
    suspend fun findAllDevice(
        @Header("x-api-key") header: String
    ): List<DeviceResponse>

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("/devices/{device}/status")
    suspend fun findDevice(
        @Path(value = "device", encoded = true) device: String,
        @Header("x-api-key") header: String,
        @Body updateDeviceStatusRequest: UpdateDeviceStatusRequest
    ): UpdateDeviceStatusResponse

    @GET("/enviroment/latest")
    suspend fun getEnvironmentData(
        @Header("x-api-key") header: String
    ): EnvironmentData
}