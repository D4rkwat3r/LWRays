package com.darkwater.lwrays.models

data class MessageSendingRequest(
    val t: Int,
    val threadId: Long,
    val msg: Message
)
