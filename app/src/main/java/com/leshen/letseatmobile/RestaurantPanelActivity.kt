package com.leshen.letseatmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RestaurantPanelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_panel)

        // Retrieve data from the intent
        val restaurantId = intent.getIntExtra("restaurantId", -1)
        val restaurantName = intent.getStringExtra("restaurantName")

        // Use the data as needed
        val restaurantNameTextView = findViewById<TextView>(R.id.restaurantPanelRestaurantName)
        restaurantNameTextView.text = restaurantName
    }
}