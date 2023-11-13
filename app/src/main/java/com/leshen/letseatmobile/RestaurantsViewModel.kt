package com.leshen.letseatmobile

import com.leshen.letseatmobile.model.Restaurant
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RestaurantsUiState(
    val restaurants: List<Restaurant> = emptyList(),
    val selectedCategory: String? = null
){
    val categories = restaurants.map{it.category}.toSet()
    val selectedRestaurant  = restaurants.filter { it.category == selectedCategory }
}
class RestaurantsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<RestaurantsUiState>(RestaurantsUiState())
    val uiState = _uiState.asStateFlow()

    private val httpClient = HttpClient{
        install(ContentNegotiation){
            json()
        }
    }
    init {
        updateRestaurants()
    }
    override fun onCleared() {
        httpClient.close()
    }
    fun selectCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }
    fun updateRestaurants() {
        viewModelScope.launch {
            val restaurants = getRestaurants()
            _uiState.update {
                it.copy(restaurants = restaurants)
            }
        }
    }
    private suspend fun getRestaurants(): List<Restaurant> {
        val restaurants = httpClient
            .get("https://sebi.io/demo-image-api/pictures.json")
            .body<List<Restaurant>>()
        return restaurants
    }
}