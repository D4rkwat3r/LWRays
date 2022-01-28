package com.darkwater.lwrays.models

data class APIException(
    val apiCode: Int,
    val apiMsg: String,
    val debugMsg: String?
)
