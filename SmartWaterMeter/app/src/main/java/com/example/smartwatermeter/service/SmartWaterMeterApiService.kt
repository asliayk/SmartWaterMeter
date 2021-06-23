package com.example.smartwatermeter.service


import com.example.smartwatermeter.models.*
import com.example.smartwatermeter.service.SmartWaterApi.getUnsafeOkHttpClient
import com.example.smartwatermeter.service.responses.GetFriendsResponse
import com.example.smartwatermeter.service.responses.GetQuotaResponse
import com.example.smartwatermeter.service.responses.LoginResponse
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val BASE_URL= "https://localhost:8080/"
//const val BASE_URL = "https://3.121.205.215:80/"
//const val BASE_URL= "https://3.121.205.215:443/"
//const val BASE_URL= "https:/10.0.2.2:443/"

private val requestInterceptor = Interceptor { chain ->

    val url = chain.request()
        .url()
        .newBuilder()
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()
    return@Interceptor chain.proceed(request)
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(requestInterceptor)
    .build()




private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(getUnsafeOkHttpClient().build())
    //.client(okHttpClient)
    .build()


interface SmartWaterMeterApiService {

    @FormUrlEncoded
    @POST("key")
    suspend fun key(
        @Field("appId") appId: String,
        @Field("name") name: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("eMail") eMail: String,
        @Field("eMailPass") eMailPass: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("addfriend")
    suspend fun addFriend(
        @Field("appId") appId: String,
        @Field("fId") fId: String,
        @Field("fNum") fNum: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("frequest")
    suspend fun fRequest(
        @Field("appId") appId: String
    ): Response<List<RequestModel>>

    @FormUrlEncoded
    @POST("frequest/yes")
    suspend fun fRequestYes(
        @Field("appId") appId: String,
        @Field("fId") fId: String,
        @Field("fNum") fNum: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("frequest/no")
    suspend fun fRequestNo(
        @Field("appId") appId: String,
        @Field("fId") fId: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("getfriends")
    suspend fun getFriends(
        @Field("appId") appId: String
    ): Response<GetFriendsResponse>

    @FormUrlEncoded
    @POST("delfriend")
    suspend fun delFriend(
        @Field("appId") appId: String,
        @Field("fId") fId: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("getday")
    suspend fun getDay(
        @Field("appId") appId: String
    ): Response<List<UsageModel>>

    @FormUrlEncoded
    @POST("getmonth")
    suspend fun getMonth(
        @Field("appId") appId: String
    ): Response<List<UsageModel>>

    @FormUrlEncoded
    @POST("getinvoice")
    suspend fun getInVoice(
        @Field("appId") appId: String
    ): Response<BillModel>

    @FormUrlEncoded
    @POST("getquota")
    suspend fun getQuota(
        @Field("appId") appId: String
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("setquota")
    suspend fun setQuota(
        @Field("appId") appId: String,
        @Field("quota") quota: Long
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("getcontrol")
    suspend fun getControl(
        @Field("appId") appId: String
    ): Response<List<ControlModel>>

    @FormUrlEncoded
    @POST("getname")
    suspend fun getName(
        @Field("appId") appId: String
    ): Response<NameModel>

    @FormUrlEncoded
    @POST("adduser")
    suspend fun addUser(
        @Field("aboneNo") aboneNo: Int,
        @Field("eMail") eMail: String,
        @Field("eMailPass") eMailPass: String,
        @Field("nameSurname") nameSurname: String,
    ): Response<ResponseBody>

}

object SmartWaterApi {
    fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<out X509Certificate> {
                    return arrayOf();
                }

            })
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    val smartWaterApiService: SmartWaterMeterApiService by lazy {
        retrofit.create(
            SmartWaterMeterApiService::class.java
        )
    }
}