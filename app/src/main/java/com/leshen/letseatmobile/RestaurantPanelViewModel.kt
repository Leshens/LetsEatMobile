package com.leshen.letseatmobile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leshen.letseatmobile.restaurantPanel.RestaurantPanelModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantPanelViewModel : ViewModel() {

    val restaurantData = MutableLiveData<RestaurantPanelModel>()
    val errorMessage = MutableLiveData<String>()

    suspend fun fetchDataFromApi(restaurantId: Int) {
        try {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.d("HTTP_LOG", message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val apiService = Retrofit.Builder()
                .baseUrl("http://31.179.139.182:690")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)

            val restaurant = apiService.getRestaurantPanelData(restaurantId)

            Log.d("HTTP_RESPONSE_PANEL", "Received data from API: $restaurant")

            // Update LiveData with the fetched data
            restaurantData.postValue(restaurant)

        } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) {
                Log.e("HTTP_ERROR_404", "Resource not found (HTTP 404)", e)
                // Handle 404 error appropriately
                errorMessage.postValue("Failed to fetch restaurant details")
            } else {
                Log.e("HTTP_ERROR_Else", "HTTP error: ${e.code()}", e)
                // Handle other HTTP errors
                errorMessage.postValue("Failed to fetch restaurant details")
            }
        } catch (e: Exception) {
            Log.e("HTTP_ERROR_OTHER", "Error fetching data from API", e)
            errorMessage.postValue("Failed to connect to the server")
        }
    }
}

