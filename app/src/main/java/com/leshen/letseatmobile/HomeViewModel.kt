package com.leshen.letseatmobile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leshen.letseatmobile.restautrant.RestaurantModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    private val _restaurants = MutableLiveData<List<RestaurantModel>>()
    val restaurants: LiveData<List<RestaurantModel>> get() = _restaurants

    private val _selectedRange = MutableLiveData<Int>()
    val selectedRange: LiveData<Int> get() = _selectedRange

    init {
        _selectedRange.value = 1
        fetchDataFromApi()
    }

    fun fetchDataFromApi() {
        viewModelScope.launch {
            try {
                val apiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)

                val restaurants = apiService.getRestaurants()
                _restaurants.value = restaurants

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateSelectedRange(range: Int) {
        _selectedRange.value = range
    }
}