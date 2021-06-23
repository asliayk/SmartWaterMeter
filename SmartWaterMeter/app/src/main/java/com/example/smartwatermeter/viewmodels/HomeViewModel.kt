package com.example.smartwatermeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.doneAlert
import com.example.smartwatermeter.fragments.SetQuotaFragment
import com.example.smartwatermeter.service.SmartWaterApi
import kotlinx.coroutines.*
import okhttp3.ResponseBody

class HomeViewModel: ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }


    private val _key = MutableLiveData<ResponseBody>()
    val key: LiveData<ResponseBody>
        get() = _key

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _quota = MutableLiveData<Int>()
    val quota: LiveData<Int>
        get() = _quota

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack




    fun getName() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.getName(
                    it)
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _name.value = it!!.name0

                        }
                    }
                }
            }
        }
    }

    fun getQuota() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.getQuota(
                    it)
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            var s = it!!.string()
                            if(s!="None")
                            _quota.value = s.toInt()
                            else
                                _quota.value = 100
                        }
                    }
                }
            }
        }
    }

    fun setQuota(fragment: SetQuotaFragment, quota: Long) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.setQuota(
                    it, quota)
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        _navigateBack.value = true
                    }
                }
            }
        }
    }

    fun resetNavigate() {
        _navigateBack.value = false
    }









}