package com.leshen.letseatmobile.API

import retrofit2.http.GET

interface ApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): List<RestaurantModel>
}