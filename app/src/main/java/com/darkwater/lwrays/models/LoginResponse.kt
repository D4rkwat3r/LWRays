package com.darkwater.lwrays.models

data class LoginResponse(
    val sId: String,
    val secret: String,
    val account: Account
)
