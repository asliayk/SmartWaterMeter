package com.example.smartwatermeter.service.responses

data class LoginResponse(
    var appId: String,
    var server: String,
    var port: Int,
    var username: String,
    var password: String,
    var appTopic: String,
    var appCont_topic: String

)
