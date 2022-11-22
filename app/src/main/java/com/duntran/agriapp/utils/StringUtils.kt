package com.duntran.agriapp.utils

object StringUtils {
    fun standardized(str: String): String {
        str.lowercase()
        return str.replaceFirst(str[0] + "", str[0].uppercase() + "")
    }
}