package com.darkwater.lwrays.models

data class Message(
    val type: Int,
    val status: Int,
    val threadId: Long,
    val uid: Long,
    val content: String,
    val seqId: Long = System.currentTimeMillis(),
    val createdTime: Long = System.currentTimeMillis(),
    val messageId: Long = 0
)
