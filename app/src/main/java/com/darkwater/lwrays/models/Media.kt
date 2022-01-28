package com.darkwater.lwrays.models

data class Media(
    val mediaId: Long,
    val baseUrl: String,
    val resourceList: List<Resource>
)
