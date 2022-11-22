package com.duntran.agriapp.ui.main.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.duntran.agriapp.data.repository.EnviDataRepository
import com.duntran.agriapp.utils.Constants
import com.duntran.agriapp.utils.SharedPreferenceUtils
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus




class DataService : Service() {
    private val enviDataRepository = EnviDataRepository()
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        listenData(this)
        return START_STICKY
    }

    private fun listenData(context: Context) {
        scope.launch {
            while (true) {
                val res = enviDataRepository.getData(
                    SharedPreferenceUtils.getInstance(context).getStringValue(
                        Constants.TOKEN_KEY)?:"")
//                Log.e("----", "listenData: ${res}")
                EventBus.getDefault().post(res)
                delay(1000)
            }
        }

    }
}