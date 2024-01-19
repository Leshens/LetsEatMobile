package com.leshen.letseatmobile.restaurantList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leshen.letseatmobile.R

class RestaurantListAdapter(
    var filteredList: List<RestaurantListModel>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    private val favoriteStates = mutableMapOf<Int, Boolean>()
    private val sizeCountMap = mutableMapOf<Int, Int>()

    fun updateData(newList: List<RestaurantListModel>?) {
        filteredList = newList ?: emptyList()
        countTableSizes()
        notifyDataSetChanged()
    }


    fun filterByCategory(category: String) {
        filteredList = if (filteredList != null && category.isNotEmpty()) {
            filteredList.filter { it.restaurantCategory == category }
        } else {
            filteredList
        }
        countTableSizes()
        notifyDataSetChanged()
    }

    private fun countTableSizes() {
        sizeCountMap.clear()

        filteredList?.let { list ->
            list.flatMap { it.tableModels.orEmpty() }
                .groupingBy { it.size }
                .eachCount()
                .forEach { (size, count) ->
                    sizeCountMap[size] = count
                }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(restaurantListModel: RestaurantListModel)
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
        holder.starTextView.text = restaurant.stars.toString()

        // Update tablesTextView with the size and count information
        val sizeText = generateSizeText(restaurant.tableModels)
        holder.tablesTextView.text = sizeText

        // Load the restaurant picture using Glide
        Glide.with(holder.itemView.context)
            .load(restaurant.photoLink)
            .placeholder(R.drawable.template_restauracja)
            .error(R.drawable.template_restauracja)
            .centerCrop()
            .into(holder.restaurantPictureImageView)

        // Set the favorite state for the current position
        favoriteStates[position] = false
        holder.favoriteButton.setOnClickListener {
            // Handle favorite button click
            val currentState = favoriteStates[position] ?: false

            if (currentState) {
                holder.favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                holder.favoriteButton.setImageResource(R.drawable.baseline_favorite_24)
            }
            favoriteStates[position] = !currentState
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    private fun generateSizeText(tableModels: List<TableModel>?): String {
        return tableModels.orEmpty()
            .groupingBy { it.size }
            .eachCount()
            .entries.joinToString("\n") { (size, count) ->
                val sizeCount = sizeCountMap[size] ?: 0
                "$count stolik/i($size os.)"
            }
    }
}
