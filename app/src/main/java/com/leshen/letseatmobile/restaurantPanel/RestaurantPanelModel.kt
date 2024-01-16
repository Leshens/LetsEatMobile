package com.leshen.letseatmobile.restaurantPanel

data class RestaurantPanelModel(
    val averageAtmosphere: Double,
    val averageFood: Double,
    val averageService: Double,
    val latitude: Double,
    val location: String,
    val longitude: Double,
    val menuModels: List<MenuModel>,
    val openingHours: String,
    val phoneNumber: String,
    val photoLink: String,
    val restaurantId: Int,
    val restaurantName: String,
    val reviewModels: List<ReviewModel>,
    val websiteLink: String
)