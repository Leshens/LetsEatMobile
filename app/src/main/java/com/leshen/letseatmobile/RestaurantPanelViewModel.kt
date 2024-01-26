package com.leshen.letseatmobile

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leshen.letseatmobile.restaurantPanel.RestaurantPanelModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RestaurantPanelViewModel : ViewModel() {

    val restaurantData = MutableLiveData<RestaurantPanelModel>()
    val errorMessage = MutableLiveData<String>()

    suspend fun fetchDataFromApi(restaurantId: Int) {
        try {
            // Create an interceptor for logging HTTP requests and responses
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.d("HTTP_LOG", message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // Create an OkHttpClient with the logging interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // Create a Retrofit instance
            val apiService = Retrofit.Builder()
                .baseUrl("http://31.179.139.182:690")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiService::class.java)

            // Make an API request to get restaurant data
            val restaurant = apiService.getRestaurantPanelData(restaurantId)

            // Log the received data
            Log.d("HTTP_RESPONSE_PANEL", "Received data from API: $restaurant")

            // Update LiveData with the fetched data
            restaurantData.postValue(restaurant)

        } catch (e: retrofit2.HttpException) {
            // Handle HTTP exceptions, such as 404
            if (e.code() == 404) {
                Log.e("HTTP_ERROR_404", "Resource not found (HTTP 404)", e)
                errorMessage.postValue("Failed to fetch restaurant details")
            } else {
                Log.e("HTTP_ERROR_Else", "HTTP error: ${e.code()}", e)
                errorMessage.postValue("Failed to fetch restaurant details")
            }
        } catch (e: Exception) {
            // Handle other exceptions, such as network errors
            Log.e("HTTP_ERROR_OTHER", "Error fetching data from API", e)
            errorMessage.postValue("Failed to connect to the server")
        }
    }
    suspend fun submitReview(
        restaurantId: Int,
        token: String,
        comment: String,
        atmosphere: Int,
        food: Int,
        service: Int
    ): String {
        try {
            val requestBody = JSONObject().apply {
                put("restaurantId", restaurantId)
                put("token", token)
                put("atmosphere", atmosphere)
                put("comment", comment)
                put("food", food)
                put("service", service)
            }.toString().toRequestBody("application/json".toMediaType())

            val response = withContext(Dispatchers.IO) {
                val apiService = Retrofit.Builder()
                    .baseUrl("http://31.179.139.182:690")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient())
                    .build()
                    .create(ApiService::class.java)

                apiService.submitReview(requestBody)
            }
            if (response.isSuccessful) {
                return "opinia została dodana"
            } else {
                // Handle specific error responses
                when (response.code()) {
                    400 -> {
                        // Another review with the same token exists within half a year
                        val errorResponse = response.errorBody()?.string()
                        val errorMessageJson = JSONObject(errorResponse ?: "")
                        val errorMessageString =
                            errorMessageJson.optString("message", "Error submitting review")
                        return errorMessageString
                    }
                    else -> {
                        errorMessage.postValue("Error submitting review")
                        return "Error submitting review"
                    }
                }
            }

        } catch (e: Exception) {
            errorMessage.postValue("Error submitting review")
            return "Error submitting review"
        }
    }
}
