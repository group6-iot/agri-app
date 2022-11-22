package com.duntran.agriapp.utils

import com.duntran.agriapp.R
import com.duntran.agriapp.ui.main.HomeItem

object Constants {
    const val BASE_URL = "https://group6-iot.herokuapp.com/"
    const val LOGIN_URL = "users/login"
    const val TOKEN_KEY = "TOKEN_KEY"
    const val LIMIT_ITEM_PER_PAGE = 10

    val LIST_ITEM_HOME = listOf(
        HomeItem("Light", R.drawable.ic_soil, "10 g/m³"),
        HomeItem("Humidity", R.drawable.ic_humidity, "20 g/m³"),
//        HomeItem("Temperature", R.drawable.ic_temperature, "35 °C"),
        HomeItem("Device Setting", R.drawable.ic_device, " "),
    )
}