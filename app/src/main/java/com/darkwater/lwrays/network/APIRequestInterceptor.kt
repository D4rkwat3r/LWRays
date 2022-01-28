package com.darkwater.lwrays.network

import android.util.Log
import com.darkwater.lwrays.utils.SessionHolder
import com.darkwater.lwrays.utils.ZUtils
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class APIRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headersBuilder = Headers.Builder()
            .add("appType", "MainApp")
            .add("appVersion", "1.23.4")
            .add("osType", "2")
            .add("deviceType", "1")
            .add("Accept-Language", "en-US")
            .add("countryCode", "EN")
            .add("User-Agent", "LWRays/1.1 RELEASE")
            .add("timeZone", "480")
            .add("carrierCountryCodes", "en")
            .add("Content-Type", "application/json; charset=UTF-8")
        request.headers.forEach {
            headersBuilder.add(it.first, it.second)
        }
        if (SessionHolder.extractDeviceId() != null) {
            headersBuilder.add("rawDeviceId", SessionHolder.extractDeviceId()!!)
        }
        if (SessionHolder.extractSession() != null) {
            headersBuilder.add("sId", SessionHolder.extractSession()!!)
        }
        val headers = headersBuilder.build()
        val newRequest = request.newBuilder()
            .headers(headers)
        val body = if (request.body != null) {
            request.body
        } else if (request.method == "POST" && request.body == null) {
            "".toRequestBody("application/json; charset=UTF-8".toMediaType())
        } else {
            null
        }
        newRequest.addHeader("HJTRFS", ZUtils.signature(
                ZUtils.getPath(request.url.toString()),
                headers,
                body
            ))
        return chain.proceed(newRequest.build())
    }
}