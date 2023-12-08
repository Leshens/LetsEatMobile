package com.leshen.letseatmobile.API

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.leshen.letseatmobile.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope

class RestaurantAdapter(var originalList: List<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var filteredList: List<RestaurantModel> = originalList
    private val favoriteStates = mutableMapOf<Int, Boolean>() // Map to store favorite states by position
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    fun updateData(newList: List<RestaurantModel>) {
        originalList = newList
        filteredList = originalList
        notifyDataSetChanged()
    }

    fun filterByCategory(category: String) {
        filteredList = if (category.isNotEmpty()) {
            originalList.filter { it.restaurantCategory == category }
        } else {
            originalList
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.listCardView)
        val tablesTextView: TextView = itemView.findViewById(R.id.listRestaurantTables)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.listFavoriteButton)
        val nameTextView: TextView = itemView.findViewById(R.id.listRestaurantName)
        val starTextView: TextView = itemView.findViewById(R.id.listRestaurantStar)
        val distanceTextView: TextView = itemView.findViewById(R.id.listRestaurantDistance)
        val timeTextView: TextView = itemView.findViewById(R.id.listRestaurantTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = filteredList[position]

        holder.nameTextView.text = restaurant.restaurantName
        // Set data to views
        holder.tablesTextView.text = "1 stolik(2 os.) \n2 stolik(4 os.)"
        // Set other properties similarly

        // Initialize favorite state for the current position
        favoriteStates[position] = false

        // Example of setting click listener on the favorite button
        holder.favoriteButton.setOnClickListener {
            // Handle favorite button click
            val currentState = favoriteStates[position] ?: false

            if (currentState) {
                // If the current state is true, change it to false and set the second icon
                holder.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                // If the current state is false, change it to true and set the first icon
                holder.favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
            }

            // Update the favorite state for the current position
            favoriteStates[position] = !currentState
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }
}


