package com.duntran.agriapp.ui.envidetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.duntran.agriapp.data.api.response.DataResponse
import com.duntran.agriapp.data.api.response.LoadingStatus
import com.duntran.agriapp.data.model.LoginSuccessResponse
import com.duntran.agriapp.data.repository.AuthRepository
import com.duntran.agriapp.data.repository.DeviceRepository
import com.duntran.agriapp.utils.Constants.TOKEN_KEY
import com.duntran.agriapp.utils.SharedPreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnviDetailViewModel(private val app: Application) : ViewModel() {
    private var authRepository = AuthRepository()
    private var deviceRepository = DeviceRepository()
    private var _token = MutableLiveData<DataResponse<LoginSuccessResponse>>(DataResponse.DataIdle())
    val token: LiveData<DataResponse<LoginSuccessResponse>> = _token

    val isLoading: LiveData<Boolean> = Transformations.map(_token) {
        _token.value?.loadingStatus == LoadingStatus.Loading
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_token.value?.loadingStatus != LoadingStatus.Loading) {
                _token.value?.loadingStatus = LoadingStatus.Loading
                val mToken = authRepository.login(username, password)

                if (mToken != null) {
                    _token.postValue(DataResponse.DataSuccess(mToken))
                }
                else {
                    _token.postValue(DataResponse.DataError())
                }
            }

        }
    }


    class Factory(private val app: Application) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EnviDetailViewModel::class.java)) {
                return EnviDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}