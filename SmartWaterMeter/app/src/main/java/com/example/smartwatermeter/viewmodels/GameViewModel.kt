package com.example.smartwatermeter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.models.RequestModel
import com.example.smartwatermeter.models.UsageModel
import com.example.smartwatermeter.service.SmartWaterApi
import com.example.smartwatermeter.service.responses.GetFriendsResponse
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONArray
import kotlin.reflect.full.memberProperties

class GameViewModel : ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }


    private val _addF = MutableLiveData<ResponseBody>()
    val addF: LiveData<ResponseBody>
        get() = _addF

    private val _accept = MutableLiveData<Boolean>()
    val accept: LiveData<Boolean>
        get() = _accept

    private val _reject = MutableLiveData<Boolean>()
    val reject: LiveData<Boolean>
        get() = _reject

    private val _friends = MutableLiveData<List<String>>()
    val friends: LiveData<List<String>>
        get() = _friends

    private val _dusages = MutableLiveData<List<UsageModel>>()
    val dusages: LiveData<List<UsageModel>>
        get() = _dusages

    private val _musages = MutableLiveData<List<UsageModel>>()
    val musages: LiveData<List<UsageModel>>
        get() = _musages

    private val _requests = MutableLiveData<List<RequestModel>>()
    val requests: LiveData<List<RequestModel>>
        get() = _requests

    private val _delRes = MutableLiveData<ResponseBody>()
    val delRes: LiveData<ResponseBody>
        get() = _delRes

    fun addFriend(fId: String, fNum: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response =
                MainActivity.StaticData.loginData?.appId?.let {
                    SmartWaterApi.smartWaterApiService.addFriend(
                        it, fId, fNum)
                }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _addF.value = it
                        }
                    }
                }
            }
        }
    }

    fun getFriends() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.getFriends(
                    it
                )
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            if (it != null) {
                                _friends.value = it.friendList
                            }

                        }
                    }
                }
            }
        }
    }

    fun acceptRequest(requestModel: RequestModel, number: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.fRequestYes(
                    it,requestModel.appId,number)
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _accept.value = true
                            getRequests()
                            getMonth()
                        }
                    }
                }
            }
        }
    }

    fun rejectRequest(requestModel: RequestModel) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.fRequestNo(
                    it,requestModel.appId)
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _reject.value = true
                            getRequests()
                        }
                    }
                }
            }
        }
    }

    fun deleteFriend(fId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            println("jobta")
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.delFriend(
                    it, fId)
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _delRes.value = it
                            getMonth()
                        }
                    }
                }
            }
        }
    }

    fun getDay() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.getDay(
                    it
                )
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _dusages.value = it
                        }
                    }
                }
            }
        }
    }

    fun getMonth() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.getMonth(
                    it
                )
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _musages.value = it
                        }
                    }
                }
            }
        }
    }

    fun getRequests() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = MainActivity.StaticData.loginData?.appId?.let {
                SmartWaterApi.smartWaterApiService.fRequest(
                    it
                )
            }
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { it ->
                            _requests.value = it
                        }
                    }
                }
            }
        }
    }


}