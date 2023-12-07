package com.leshen.letseatmobile


import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.leshen.letseatmobile.databinding.ActivityMainBinding
import com.leshen.letseatmobile.login.GetStartedActivity

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        super.onDestroy()
        binding = null
    }
}