package com.duntran.agriapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getSimpleDate(str: String): String {
        val upDate = LocalDateTime.parse(str)
        return String.format("${upDate.hour}h${upDate.minute}m - ${upDate.dayOfMonth}/${upDate.monthValue}")
    }
}