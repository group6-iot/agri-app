package com.duntran.agriapp.ui.setupdevice

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.duntran.agriapp.data.model.DeviceMode
import com.duntran.agriapp.data.model.DeviceResponse
import com.duntran.agriapp.data.model.devicemode.UpdateDeviceModeRequest
import com.duntran.agriapp.data.repository.DeviceRepository
import com.duntran.agriapp.utils.Constants
import com.duntran.agriapp.utils.SharedPreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SetupDeviceViewModel(private val app: Application):ViewModel() {
    private var _device = MutableLiveData<DeviceResponse>()
    val device: LiveData<DeviceResponse> = _device
    private val deviceRepository = DeviceRepository()
    var scheduleStatus = MutableLiveData<Boolean>(false)

    val deviceMode: LiveData<DeviceMode> = Transformations.map(_device) {
        when (_device.value?.mode) {
            "auto" -> DeviceMode.Auto
            "manual" -> DeviceMode.Manual
            else -> DeviceMode.Schedule
        }
    }

    fun setDevice(deviceResponse: DeviceResponse) {
        _device.postValue(deviceResponse)
    }

    fun changeDeviceOnOff() {
        viewModelScope.launch(Dispatchers.IO) {
            _device.value?.let {
                val response = deviceRepository.changeStatus(
                    _device.value!!._id,
                    SharedPreferenceUtils.getInstance(app.applicationContext).getStringValue(Constants.TOKEN_KEY)
                        ?: "",
                    if (_device.value?.status == "on") {
                        "off"
                    }
                    else {
                        "on"
                    }
                )
                response?.let {
                    val dv = _device.value!!.copy()
                    dv.status = it.status
                    _device.postValue(dv)
                }
            }
        }
    }

    fun changeDeviceMode(mode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (mode != _device.value?.mode) {
                _device.value?.let {
                    val response = deviceRepository.changeMode(
                        _device.value!!._id,
                        SharedPreferenceUtils.getInstance(app.applicationContext).getStringValue(Constants.TOKEN_KEY)
                            ?: "", UpdateDeviceModeRequest(mode, null, null)
//                        when(mode) {
//                            "schedule"-> UpdateDeviceModeRequest(mode, "%d %d * * *".format(h, m), 120)
//                            else -> UpdateDeviceModeRequest(mode, null, null)
//                        }
                    )
                    response?.let {
                        val dv = _device.value!!.copy()
                        dv.mode = it.mode
                        _device.postValue(dv)
                    }
                }
            }
        }
    }

    fun schedule(h: Int, m: Int, duration: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _device.value?.let {
                val response = deviceRepository.changeMode(
                    _device.value!!._id,
                    SharedPreferenceUtils.getInstance(app.applicationContext).getStringValue(Constants.TOKEN_KEY)
                        ?: "",UpdateDeviceModeRequest("schedule", "%d %d * * *".format(m, h), duration)
                )
                response?.let {
                    val dv = _device.value!!.copy()
                    dv.mode = it.mode
                    _device.postValue(dv)
                    scheduleStatus.postValue(true)
                    delay(500)
                    scheduleStatus.postValue(false)
                }
            }
        }
    }




    @Suppress("UNCHECKED_CAST")
    class Factory(private val application: Application) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SetupDeviceViewModel::class.java)) {
                return SetupDeviceViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
