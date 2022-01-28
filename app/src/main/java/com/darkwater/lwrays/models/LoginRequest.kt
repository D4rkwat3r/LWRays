package com.darkwater.lwrays.models

data class LoginRequest(
    val email: String,
    val password: String,
    val authType: Int = 1
)
