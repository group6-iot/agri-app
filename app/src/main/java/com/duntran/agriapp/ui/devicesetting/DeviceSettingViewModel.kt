package com.duntran.agriapp.ui.devicesetting

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.duntran.agriapp.data.api.response.DataResponse
import com.duntran.agriapp.data.api.response.LoadingStatus
import com.duntran.agriapp.data.model.DeviceResponse
import com.duntran.agriapp.data.model.LoginSuccessResponse
import com.duntran.agriapp.data.repository.AuthRepository
import com.duntran.agriapp.data.repository.DeviceRepository
import com.duntran.agriapp.utils.Constants.TOKEN_KEY
import com.duntran.agriapp.utils.SharedPreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeviceSettingViewModel(private val app: Application) : ViewModel() {
    private var deviceRepository = DeviceRepository()
    private var _devices =
        MutableLiveData<DataResponse<List<DeviceResponse>>>(DataResponse.DataIdle())
    val device: LiveData<DataResponse<List<DeviceResponse>>> = _devices

    val isLoading: LiveData<Boolean> = Transformations.map(_devices) {
        _devices.value!!.loadingStatus == LoadingStatus.Loading
    }

    init {
        getAll()
    }

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_devices.value!!.loadingStatus != LoadingStatus.Loading) {
                _devices.value!!.loadingStatus = LoadingStatus.Loading

                val d = deviceRepository.findAllDevice(
                    SharedPreferenceUtils.getInstance(app.applicationContext)
                        .getStringValue(TOKEN_KEY) ?: ""
                )

                if (d.isNullOrEmpty()) {
                    _devices.postValue(DataResponse.DataError())
                } else {
                    _devices.postValue(DataResponse.DataSuccess(d))
                }
            }
        }
    }


    class Factory(private val app: Application) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DeviceSettingViewModel::class.java)) {
                return DeviceSettingViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}