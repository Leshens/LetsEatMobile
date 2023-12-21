package com.leshen.letseatmobile

import com.leshen.letseatmobile.restautrant.RestaurantModel
import retrofit2.http.GET

interface ApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): List<RestaurantModel>
}