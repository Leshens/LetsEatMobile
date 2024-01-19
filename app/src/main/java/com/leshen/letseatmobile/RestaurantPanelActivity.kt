package com.leshen.letseatmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.leshen.letseatmobile.restaurantPanel.RestaurantPanelModel
import kotlinx.coroutines.launch
class RestaurantPanelActivity : AppCompatActivity() {

    private lateinit var restaurantPanelViewModel: RestaurantPanelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_restaurant_panel)

        // Retrieve data from the intent
        val restaurantId = intent.getIntExtra("restaurantId", -1)

        // Initialize ViewModel
        restaurantPanelViewModel = ViewModelProvider(this).get(RestaurantPanelViewModel::class.java)

        // Observe LiveData
        restaurantPanelViewModel.restaurantData.observe(this) { restaurant ->
            // Update UI with fetched data
            updateUI(restaurant)
        }

        restaurantPanelViewModel.errorMessage.observe(this) { errorMessage ->
            // Handle error message
            // You can display an error message to the user or take appropriate action
        }

        // Fetch data from API using coroutine only when the activity is created
        if (restaurantPanelViewModel.restaurantData.value == null) {
            lifecycleScope.launch {
                restaurantPanelViewModel.fetchDataFromApi(restaurantId)
            }
        }

        val returnButton = findViewById<ImageButton>(R.id.restaurantPanelReturnButton)
        returnButton.setOnClickListener {
            finish()
        }
    }

    private fun updateUI(restaurant: RestaurantPanelModel) {
        val restaurantNameTextView: TextView = findViewById(R.id.restaurantPanelRestaurantName)
        restaurantNameTextView.text = restaurant.restaurantName

        val starTextView: TextView = findViewById(R.id.restaurantPanelRestaurantStar)
        starTextView.text = "${(restaurant.averageFood + restaurant.averageAtmosphere + restaurant.averageService) / 3}"

        val distanceTextView: TextView = findViewById(R.id.restaurantPanelRestaurantDistance)
        distanceTextView.text = "0.3 Km"

        val timeTextView: TextView = findViewById(R.id.restaurantPanelRestaurantTime)
        timeTextView.text = restaurant.openingHours

        val locationTextView: TextView = findViewById(R.id.restaurantPanelLocation)
        locationTextView.text = restaurant.location

        val menuTextView: TextView = findViewById(R.id.restaurantPanelMenu)
        menuTextView.text = restaurant.menuModels?.joinToString("\n") { it.name } ?: ""

        val foodTextView: TextView = findViewById(R.id.restaurantPanelFood)
        foodTextView.text = "Food: ${restaurant.averageFood} / 5"

        val atmosphereTextView: TextView = findViewById(R.id.restaurantPanelAtmosphere)
        atmosphereTextView.text = "Atmosphere: ${restaurant.averageAtmosphere} / 5"

        val serviceTextView: TextView = findViewById(R.id.restaurantPanelService)
        serviceTextView.text = "Service: ${restaurant.averageService} / 5"

        val restaurantImageView: ImageView = findViewById(R.id.restaurantImageView)
        // Load the restaurant image using Glide
        Glide.with(this)
            .load(restaurant.photoLink)
            .placeholder(R.drawable.template_restauracja)
            .error(R.drawable.template_restauracja)
            .centerCrop()
            .into(restaurantImageView)
    }
}
