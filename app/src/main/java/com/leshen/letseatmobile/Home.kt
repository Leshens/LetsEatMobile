package com.leshen.letseatmobile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leshen.letseatmobile.API.ApiService
import com.leshen.letseatmobile.API.RestaurantAdapter
import com.leshen.letseatmobile.API.RestaurantModel
import com.leshen.letseatmobile.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment() {

    private lateinit var filterLayoutHome: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create an instance of your item click listener
        val itemClickListener = object : RestaurantAdapter.OnItemClickListener {
            override fun onItemClick(restaurantModel: RestaurantModel) {
                // Use an explicit intent to start RestaurantPanelActivity
                val intent = Intent(requireContext(), RestaurantPanelActivity::class.java)
                // Pass any necessary data to the activity
                intent.putExtra("restaurantId", restaurantModel.restaurantId)
                intent.putExtra("restaurantName", restaurantModel.restaurantName)
                startActivity(intent)
            }

            override fun onFavoriteButtonClick(restaurantId: Int) {
                // Handle favorite button click if needed
            }
        }

        // Pass the item click listener to your adapter
        adapter = RestaurantAdapter(emptyList(), itemClickListener)
        recyclerView.adapter = adapter

        filterLayoutHome = binding.filterLayoutHome
        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            fetchDataFromApi()
        }

        fetchDataFromApi()

        return view
    }

    private fun fetchDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/") // Use the IP address instead of localhost
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)

                val restaurants = apiService.getRestaurants()
                withContext(Dispatchers.Main) {
                    adapter.updateData(restaurants)
                    swipeRefreshLayout.isRefreshing = false

                    val uniqueCategories = restaurants.mapNotNull { it.restaurantCategory }.distinct()
                    generateCategoryButtons(uniqueCategories)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun filterByCategory(category: String) {
        adapter.filterByCategory(category)
    }

    private fun generateCategoryButtons(categories: List<String>) {
        filterLayoutHome.removeAllViews()

        for (category in categories) {
            val button = Button(requireContext())
            button.text = category
            button.setOnClickListener {
                filterByCategory(category)
            }
            filterLayoutHome.addView(button)
        }
    }

}
