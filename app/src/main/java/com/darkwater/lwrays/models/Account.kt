package com.darkwater.lwrays.models

data class Account(
    val uid: Long,
    val status: Int,
    val email: String,
    val createdTime: String,
    val deviceId: String,
    val hasProfile: Int,
    val hasPassword: Int,
    val currentDeviceId: String,
    val currentDeviceId2: String,
    val registeredDeviceId: String,
    val registeredDeviceId2: String,
    val registeredIpv4: String,
    val lastLoginIpv4: String
)
