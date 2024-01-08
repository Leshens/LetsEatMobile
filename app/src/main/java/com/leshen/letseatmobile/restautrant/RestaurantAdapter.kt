package com.leshen.letseatmobile.restautrant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leshen.letseatmobile.R

class RestaurantAdapter(
    var originalList: List<RestaurantModel>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    private var filteredList: List<RestaurantModel> = originalList
    private val favoriteStates = mutableMapOf<Int, Boolean>() // Map to store favorite states by position

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

    interface OnItemClickListener {
        fun onItemClick(restaurantModel: RestaurantModel)
        fun onFavoriteButtonClick(restaurantId: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tablesTextView: TextView = itemView.findViewById(R.id.listRestaurantTables)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.listFavoriteButton)
        val nameTextView: TextView = itemView.findViewById(R.id.listRestaurantName)
        val restaurantPictureImageView: ImageView = itemView.findViewById(R.id.listRestaurantPicture)
        val starTextView: TextView = itemView.findViewById(R.id.listRestaurantStar)
        val distanceTextView: TextView = itemView.findViewById(R.id.listRestaurantDistance)
        val timeTextView: TextView = itemView.findViewById(R.id.listRestaurantTime)

        init {
            // Set click listener on the whole item view
            itemView.setOnClickListener {
                itemClickListener.onItemClick(filteredList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = filteredList[position]
        // Set data to views
        holder.nameTextView.text = restaurant.restaurantName
        holder.timeTextView.text = restaurant.openingHours
        holder.tablesTextView.text = "1 stolik(2 os.) \n2 stolik(4 os.)"

        // Load the restaurant picture using Glide
        Glide.with(holder.itemView.context)
            .load(restaurant.photoLink)  // Replace with the actual image URL
            .placeholder(R.drawable.template_restauracja)  // Placeholder image while loading
            .error(R.drawable.template_restauracja)  // Image to display in case of an error
            .centerCrop()
            .into(holder.restaurantPictureImageView)

        // Set the favorite state for the current position
        favoriteStates[position] = false
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
