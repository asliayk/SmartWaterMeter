package com.example.smartwatermeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.models.BillModel
import com.example.smartwatermeter.service.SmartWaterApi
import kotlinx.coroutines.*
import okhttp3.ResponseBody

class BillViewModel: ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    private val _bill = MutableLiveData<BillModel>()
    val bill: LiveData<BillModel>
        get() = _bill


    fun getBill() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = SmartWaterApi.smartWaterApiService.getInVoice(MainActivity.StaticData.loginData!!.appId)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _bill.value = it
                        println(response.body().toString())
                    }
                }
            }
        }
    }
}