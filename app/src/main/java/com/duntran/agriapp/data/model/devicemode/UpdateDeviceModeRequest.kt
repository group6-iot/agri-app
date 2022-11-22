package com.duntran.agriapp.data.model.devicemode

import java.time.Duration

data class UpdateDeviceModeRequest(
    val mode: String,
    var cron: String?,
    var duration: Int?,
)