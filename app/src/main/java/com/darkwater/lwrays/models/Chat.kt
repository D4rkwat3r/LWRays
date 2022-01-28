package com.darkwater.lwrays.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Chat(
    val threadId: Long,
    val status: Int,
    val type: Int,
    @JsonProperty("host_uid") val hostUid: Long,
    val title: String,
    val icon: Media,
    val membersCount: Int,
)
