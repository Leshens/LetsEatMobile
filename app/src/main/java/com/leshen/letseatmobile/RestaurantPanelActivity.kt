package com.leshen.letseatmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class RestaurantPanelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_restaurant_panel)

        // Retrieve data from the intent
        val restaurantId = intent.getIntExtra("restaurantId", -1)
//        val restaurantName = intent.getStringExtra("restaurantName")
//        val restaurantPictureLink = intent.getStringExtra("photoLink")
//
//        // Data from panel
//        val restaurantNameTextView = findViewById<TextView>(R.id.restaurantPanelRestaurantName)
//        val restaurantImageView = findViewById<ImageView>(R.id.restaurantImageView)
//        val backButton = findViewById<ImageButton>(R.id.restaurantPanelReturnButton)
//
//        // Data functions
//        restaurantNameTextView.text = restaurantName
//
//        // Load and display the image using Glide
//        Glide.with(this)
//            .load(restaurantPictureLink)  // Replace with the actual image URL
//            .placeholder(R.drawable.template_restauracja)  // Placeholder image while loading
//            .error(R.drawable.template_restauracja)  // Image to display in case of an error
//            .centerCrop()
//            .into(restaurantImageView)
//
//        backButton.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}