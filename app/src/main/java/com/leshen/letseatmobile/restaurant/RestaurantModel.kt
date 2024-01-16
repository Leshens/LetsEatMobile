package com.leshen.letseatmobile.restaurant

data class RestaurantModel(
    val latitude: Double,
    val location: String,
    val longitude: Double,
    val openingHours: String,
    val phoneNumber: String,
    val photoLink: String,
    val restaurantCategory: String,
    val restaurantId: Int,
    val restaurantName: String,
    val token: String,
    val websiteLink: String
)