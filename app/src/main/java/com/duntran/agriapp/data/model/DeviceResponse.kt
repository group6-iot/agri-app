package com.duntran.agriapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeviceResponse(
    val _id: String,
    val created_at: String,
    var mode: String,
    val name: String,
    var status: String,
    var cron: String?,
    var duration: Int?,
    val updated_at: String
): Parcelable