package com.example.smartwatermeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartwatermeter.models.BillModel
import com.example.smartwatermeter.service.SmartWaterApi
import kotlinx.coroutines.*

class RegisterViewModel: ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    fun register(aboneNo: Int, mail: String, password: String, name:String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = SmartWaterApi.smartWaterApiService.addUser(aboneNo,mail, password,name)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _success.value = true
                    }
                }
            }
        }
    }
}