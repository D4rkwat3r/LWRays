package com.darkwater.lwrays.network

import com.darkwater.lwrays.models.ChatListResponse
import com.darkwater.lwrays.models.CircleListResponse
import com.darkwater.lwrays.models.LoginRequest
import com.darkwater.lwrays.models.LoginResponse
import com.darkwater.lwrays.utils.SessionHolder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @POST("auth/login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>
    @POST("chat/threads/{threadId}/members")
    fun joinThread(@Path("threadId") threadId: Long): Call<ResponseBody>
    @DELETE("chat/threads/{threadId}/members")
    fun leaveThread(@Path("threadId") threadId: Long): Call<ResponseBody>
    @GET("circles")
    fun getCircles(
        @Query("type") type: String,
        @Query("size") size: Int,
        @Query("categoryId") categoryId: Int? = null,
        @Query("pageToken") pageToken: String? = null
    ): Call<CircleListResponse>
    @GET("chat/threads")
    fun getChats(
        @Query("type") type: String,
        @Query("size") size: Int,
        @Query("objectId") objectId: Long? = null,
        @Query("pageToken") pageToken: String? = null
    ): Call<ChatListResponse>
}