package com.leshen.letseatmobile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.leshen.letseatmobile.databinding.FragmentHomeBinding
import com.leshen.letseatmobile.restautrant.RestaurantAdapter
import com.leshen.letseatmobile.restautrant.RestaurantModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Home : Fragment() {

    private lateinit var filterLayoutHome: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RestaurantAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var locationButton: Button
    private var selectedRange = 1
    private var locationButtonText: String = ""

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }

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
                intent.putExtra("photoLink", restaurantModel.photoLink)
                startActivity(intent)
            }

            override fun onFavoriteButtonClick(restaurantId: Int) {
            }

        }

        // Pass the item click listener to your adapter
        adapter = RestaurantAdapter(emptyList(), itemClickListener)
        recyclerView.adapter = adapter

        filterLayoutHome = binding.filterLayoutHome
        swipeRefreshLayout = binding.swipeRefreshLayout
        locationButton = binding.locationButton

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        swipeRefreshLayout.setOnRefreshListener {
            fetchDataFromApi()
        }

        locationButton.setOnClickListener {
            checkLocationPermission()
            showRangeSelectorDialog()
        }

        fetchDataFromApi()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update location button with real location
        checkLocationPermission()
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

                    val uniqueCategories = restaurants.map { it.restaurantCategory }.distinct()
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

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission is already granted, update location
            updateLocationButton()
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationButton() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latLng = Pair(location.latitude, location.longitude)
                val address = getAddressFromLocation(latLng)

                val buttonText = "$address\nw  w promieniu $selectedRange km"
                locationButton.text = buttonText

                Log.d("Location", "Updated location: $address")
            } else {
                Log.e("Location", "Last location is null")
            }
        }
    }

    private fun getAddressFromLocation(latLng: Pair<Double, Double>): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(latLng.first, latLng.second, 1)

        val address = addresses?.getOrNull(0)
        return address?.thoroughfare ?: "Street name not found"
    }
    private fun showRangeSelectorDialog() {
        val rangeSelectorDialog = RangeSelectorDialogFragment()
        rangeSelectorDialog.setRangeSelectorListener(object : RangeSelectorDialogFragment.RangeSelectorListener {
            override fun onRangeSelected(range: Int) {
                selectedRange = range
                updateLocationButton() // Dodane przekazanie wartości zakresu do metody aktualizującej przycisk lokalizacji
                Log.d("RangeSelector", "Selected range: $range")
            }
        })
        rangeSelectorDialog.show(parentFragmentManager, "RangeSelectorDialog")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
