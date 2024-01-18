package com.leshen.letseatmobile

import com.leshen.letseatmobile.restaurantList.RestaurantListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/search")
    suspend fun getRestaurants(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int
    ): List<RestaurantListModel>

}