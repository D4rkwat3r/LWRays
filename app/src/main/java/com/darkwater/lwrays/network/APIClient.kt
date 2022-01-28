package com.darkwater.lwrays.network

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.darkwater.lwrays.models.*
import com.darkwater.lwrays.utils.SessionHolder
import com.darkwater.lwrays.utils.ZUtils
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class APIClient(private val activity: Activity) {

    val mapper = jacksonObjectMapper()
    private val httpClient = OkHttpClient.Builder()
                            .addInterceptor(APIRequestInterceptor())
                            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
                            .followRedirects(false)
                            .retryOnConnectionFailure(true)
                            .build()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        if (SessionHolder.extractDeviceId() == null) {
            SessionHolder.setDeviceId(ZUtils.deviceId())
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.projz.com/v1/")
        .addConverterFactory(JacksonConverterFactory.create(mapper))
        .client(httpClient)
        .build()

    private val api = retrofit.create(APIService::class.java)

    fun login(email: String, password: String, onSuccess: (LoginResponse) -> Unit) {
        val body = LoginRequest(
            email,
            password
        )
        api.login(body).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.errorBody() != null) {
                    val exception = mapper.readValue(response.errorBody()!!.string(), APIException::class.java)
                    apiExceptionAlert(exception)
                    return
                }
                activity.runOnUiThread { onSuccess(response.body()!!) }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                activity.runOnUiThread { Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show() }
            }

        })
    }

    fun loadCircleList(onLoad: (CircleListResponse) -> Unit) {
        api.getCircles("joined", 100).enqueue(object: Callback<CircleListResponse> {
            override fun onResponse(call: Call<CircleListResponse>, response: Response<CircleListResponse>) {
                if (response.errorBody() != null) {
                    val exception = mapper.readValue(response.errorBody()!!.string(), APIException::class.java)
                    apiExceptionAlert(exception)
                    return
                }
                activity.runOnUiThread { onLoad(response.body()!!) }
            }

            override fun onFailure(call: Call<CircleListResponse>, t: Throwable) {
                activity.runOnUiThread { Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show() }
            }

        })
    }

    fun loadChatList(circleId: Long, onLoad: (ChatListResponse) -> Unit) {
        api.getChats("circle", 150, circleId).enqueue(object: Callback<ChatListResponse> {
            override fun onResponse(call: Call<ChatListResponse>, response: Response<ChatListResponse>
            ) {
                if (response.errorBody() != null) {
                    val exception = mapper.readValue(response.errorBody()!!.string(), APIException::class.java)
                    apiExceptionAlert(exception)
                    return
                }
                activity.runOnUiThread { onLoad(response.body()!!) }
            }

            override fun onFailure(call: Call<ChatListResponse>, t: Throwable) {
                activity.runOnUiThread { Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show() }
            }

        })
    }

    fun joinChat(threadId: Long, onSuccess: () -> Unit) {
        api.joinThread(threadId).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.errorBody() != null) {
                    val exception = mapper.readValue(response.errorBody()!!.string(), APIException::class.java)
                    apiExceptionAlert(exception)
                    return
                }
                activity.runOnUiThread { onSuccess() }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity.runOnUiThread { Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show() }
            }
        })
    }

    fun leaveChat(threadId: Long, onSuccess: () -> Unit) {
        api.leaveThread(threadId).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.errorBody() != null) {
                    val exception = mapper.readValue(response.errorBody()!!.string(), APIException::class.java)
                    apiExceptionAlert(exception)
                    return
                }
                activity.runOnUiThread { onSuccess() }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity.runOnUiThread { Toast.makeText(activity, t.toString(), Toast.LENGTH_SHORT).show() }
            }
        })
    }

    fun createWebSocket(onMessage: (String) -> Unit): WebSocket {
        val request = Request.Builder()
            .get()
            .url("wss://ws.projz.com/v1/chat/ws")
            .build()
        return httpClient.newWebSocket(request, object: WebSocketListener() {
            override fun onFailure(
                webSocket: WebSocket,
                t: Throwable,
                response: okhttp3.Response?
            ) {
                Log.e("WebSocket", t.stackTraceToString())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessage(text)
            }
        })
    }

    private fun apiExceptionAlert(exception: APIException) {
        val alert = AlertDialog.Builder(activity)
        alert.setTitle("Ошибка #${exception.apiCode}")
        alert.setMessage(exception.apiMsg)
        alert.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        activity.runOnUiThread {
            alert.show()
        }
    }

}