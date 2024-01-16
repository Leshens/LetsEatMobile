package com.leshen.letseatmobile.restaurantPanel

data class ReviewModel(
    val atmosphere: Int,
    val comment: String,
    val date: Any,
    val food: Int,
    val restaurantId: Any,
    val reviewId: Int,
    val service: Int,
    val token: Long
)