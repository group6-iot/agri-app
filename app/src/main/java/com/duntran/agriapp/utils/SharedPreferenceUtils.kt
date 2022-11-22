package com.duntran.agriapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class SharedPreferenceUtils private constructor(context: Context) {
    private val PREFERENCE_NAME = "PRANK_SOUNDS"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun putAcceptPolicy(value: Boolean){
        putBooleanValue("isAcceptPolicy",value)
    }

    fun getAcceptPolicy():Boolean{
        return getBooleanValue("isAcceptPolicy")
    }

    fun putStringValue(key: String?, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value).apply()
    }

    fun getStringValue(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun putBooleanValue(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value).apply()
    }

    fun getBooleanValue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getBooleanValueWithTrueDefault(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    fun putIntValue(key: String?, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value).apply()
        editor.commit()
    }

    fun getIntValue(key: String?,defValue:Int?): Int {
        if (defValue==null){
            return sharedPreferences.getInt(key, 0)

        }
        return sharedPreferences.getInt(key, defValue)

    }

    fun putLongValue(key: String?, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value).apply()
    }

    fun getLongValue(key: String?): Long {
        return sharedPreferences.getLong(key, 0L)
    }

    companion object : SingletonHolder<SharedPreferenceUtils,Context>(::SharedPreferenceUtils)

}