package com.leshen.letseatmobile.API

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.leshen.letseatmobile.R
class RestaurantAdapter(var originalList: List<RestaurantModel>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var filteredList: List<RestaurantModel> = originalList

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = filteredList[position]

        holder.nameTextView.text = restaurant.restaurantName
        // Set data to views
        holder.tablesTextView.text = "1 stolik(2 os.) \n2 stolik(4 os.)"
        // Set other properties similarly

        // Example of setting click listener on favorite button
        holder.favoriteButton.setOnClickListener {
            // Handle favorite button click
            // For demonstration, changing the image source
            holder.favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }
}

