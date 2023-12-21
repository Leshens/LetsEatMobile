package com.leshen.letseatmobile


import android.content.ContentValues.TAG
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.leshen.letseatmobile.databinding.ActivityMainBinding
import com.leshen.letseatmobile.location.LocationService
import com.leshen.letseatmobile.login.GetStartedActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        Log.d(TAG, "onCreate: MainActivity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        replaceFragment(Home())
        auth = FirebaseAuth.getInstance()
        binding!!.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(Home())
                R.id.profile -> replaceFragment(Profile())
                R.id.favorites -> replaceFragment(Favorites())

                else ->{

                }
            }
            true
        }
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }
    fun wyloguj(view: View) {
        signOut(auth)
    }
    fun openWebsite(view: View) {
        val url = "https://www.kamelia.pl"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    fun signOut(auth: FirebaseAuth){
        if (auth.currentUser!= null) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, GetStartedActivity::class.java)
            startActivity(intent)
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_wrapper, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
        super.onDestroy()
        binding = null
    }
    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val address: Address?
        var addressText = ""

        val addresses: List<Address>? =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                address = addresses[0]
                addressText = address.getAddressLine(0)
            } else{
                addressText = "its not appear"
            }
        }
        return addressText
    }
}