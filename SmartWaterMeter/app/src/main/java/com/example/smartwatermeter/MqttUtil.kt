package com.example.smartwatermeter

/*import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.smartwatermeter.fragments.LoginFragment
import com.example.smartwatermeter.service.responses.LoginResponse
import com.example.smartwatermeter.viewmodels.HomeViewModel
import com.example.smartwatermeter.viewmodels.LoginViewModel
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

@SuppressLint("StaticFieldLeak")
private lateinit var client: MqttAndroidClient

private lateinit var homeViewModel: HomeViewModel

fun connectMqtt(context: Context, loginData: LoginResponse,fragment: LoginFragment) {
    val clientId = MqttClient.generateClientId()
    client = MqttAndroidClient(
        context, "tcp://" + loginData.server + ":" + loginData.port,
        clientId
    )
    homeViewModel = ViewModelProvider(fragment).get(HomeViewModel::class.java)

    val options = MqttConnectOptions()
    options.userName = loginData.username
    options.password = loginData.password.toCharArray()

    try {
        val token = client.connect(options)
        token.actionCallback = object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                // We are connected
                println("we are connected")
                homeViewModel._mqttConnect.value = true
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

fun handleWatermeter(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
    try {
        val message = MqttMessage()
        message.payload = msg.toByteArray()
        message.qos = qos
        message.isRetained = retained
        client.publish(topic, message, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                println("bağlandııı")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                println("bağlanmadııı")
            }
        })
    } catch (e: MqttException) {
        e.printStackTrace()
    }
}

fun subscribe(topic: String, qos: Int = 1) {
    try {
        client.subscribe(topic, qos, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                println("Subscribed to $topic")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                println("Failed to subscribe $topic")
            }
        })

    } catch (e: MqttException) {
        e.printStackTrace()
    }
}  */