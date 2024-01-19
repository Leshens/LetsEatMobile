package com.leshen.letseatmobile

import com.leshen.letseatmobile.restaurantList.RestaurantListModel
import com.leshen.letseatmobile.restaurantPanel.RestaurantPanelModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/restaurants/search")
    suspend fun getRestaurants(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int
    ): List<RestaurantListModel>

    @GET("/api/restaurants/panel/{id}")
    suspend fun getRestaurantPanelData(@Path("id") restaurantId: Int): RestaurantPanelModel

}