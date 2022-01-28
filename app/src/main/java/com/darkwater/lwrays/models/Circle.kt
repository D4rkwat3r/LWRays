package com.darkwater.lwrays.models

data class Circle(
    val circleId: Long,
    val categoryId: Long,
    val conceptId: Long,
    val socialId: String,
    val status: Int,
    val name: String,
    val icon: Media
)