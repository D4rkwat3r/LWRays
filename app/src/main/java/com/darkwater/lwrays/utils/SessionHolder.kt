package com.darkwater.lwrays.utils

object SessionHolder {
    private var session: String? = null
    private var uid: Long? = null
    private var deviceId: String? = null
    fun extractSession(): String? {
        return session
    }
    fun extractUid(): Long? {
        return uid
    }
    fun extractDeviceId(): String? {
        return deviceId
    }
    fun setSession(value: String) {
        session = value
    }
    fun setUid(value: Long) {
        uid = value
    }
    fun setDeviceId(value: String) {
        deviceId = value
    }
}