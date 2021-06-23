package com.example.smartwatermeter.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartwatermeter.activities.MainActivity
import com.example.smartwatermeter.service.SmartWaterApi
import com.example.smartwatermeter.service.responses.LoginResponse
import kotlinx.coroutines.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class LoginViewModel: ViewModel() {

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    private val _loginRes = MutableLiveData<LoginResponse>()
    val loginRes: LiveData<LoginResponse>
        get() = _loginRes

    val _mqttConnect = MutableLiveData<Boolean>()
    val mqttConnect: LiveData<Boolean>
        get() = _mqttConnect

    val _subscribed = MutableLiveData<Boolean>()
    val subscribed: LiveData<Boolean>
        get() = _subscribed

    private lateinit var client: MqttAndroidClient


    fun login(mail: String, password: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = SmartWaterApi.smartWaterApiService.login(mail, password)
            withContext(Dispatchers.Main + exceptionHandler) {
                println(response.code())
                println(response.message())
                if (response.isSuccessful) {
                    response.body().let { it ->
                        _loginRes.value = it
                        println(response.body().toString())
                    }
                }
            }
        }
    }

    fun connectMqtt(activity: MainActivity, context: Context, loginData: LoginResponse) {
        val clientId = MqttClient.generateClientId()
        client = MqttAndroidClient(
            context, "tcp://" + loginData.server + ":" + loginData.port,
            clientId
        )
        MainActivity.StaticData.mqttClient = client
        client.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                println("Receive message: ${message.toString()} from topic: $topic")
                activity.sendNotification(message.toString())
            }

            override fun connectionLost(cause: Throwable?) {
                println("Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })


        val options = MqttConnectOptions()
        options.userName = loginData.username
        options.password = loginData.password.toCharArray()

        try {
            val token = client.connect(options)
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    println("we are connected")
                    _mqttConnect.value = true
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    println("not connected")
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    fun subscribe(topic: String, qos: Int = 1) {
        try {
            client.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    println("Subscribed to $topic")
                    _subscribed.value = true

                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    println("Failed to subscribe $topic")
                }
            })

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe1(topic: String, qos: Int = 1) {
        try {
            client.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    println("Subscribed to $topic")
                    _subscribed.value = true

                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    println("Failed to subscribe $topic")
                }
            })

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}