package com.darkwater.lwrays.models

data class ChatListResponse(
    val pagination: Pagination?,
    val list: MutableList<Chat>
)
