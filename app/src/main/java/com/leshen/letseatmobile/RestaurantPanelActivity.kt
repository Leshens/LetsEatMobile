package com.leshen.letseatmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class RestaurantPanelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_panel)

        // Retrieve data from the intent
        val restaurantId = intent.getIntExtra("restaurantId", -1)
        val restaurantName = intent.getStringExtra("restaurantName")

        // Data from panel
        val restaurantNameTextView = findViewById<TextView>(R.id.restaurantPanelRestaurantName)
        val backbutton = findViewById<ImageButton>(R.id.restaurantPanelReturnButton)

        // Data functions
        restaurantNameTextView.text = restaurantName

        backbutton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            finish()
        }

    }
}