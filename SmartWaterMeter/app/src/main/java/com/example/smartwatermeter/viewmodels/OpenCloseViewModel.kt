package com.example.smartwatermeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.models.ControlModel
import com.example.smartwatermeter.service.SmartWaterApi
import kotlinx.coroutines.*

class OpenCloseViewModel: ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    private val _controlList = MutableLiveData<List<ControlModel>>()
    val controlList: LiveData<List<ControlModel>>
        get() = _controlList

    fun getControl() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = SmartWaterApi.smartWaterApiService.getControl(MainActivity.StaticData.loginData!!.appId)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _controlList.value = it
                    }
                }
            }
        }
    }
}