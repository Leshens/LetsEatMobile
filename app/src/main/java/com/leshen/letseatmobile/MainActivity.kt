package com.leshen.letseatmobile


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.leshen.letseatmobile.databinding.ActivityMainBinding
import com.leshen.letseatmobile.login.GetStartedActivity

class  : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        replaceFragment(Home())
        auth = Firebase.auth

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
        binding?.signOutButton?.setOnClickListener {
            if (auth.currentUser!= null) {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, GetStartedActivity::class.java)
                startActivity(intent)
            }

        }
    }


    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_wrapper,fragment)
        fragmentTransaction.commit()


    }
}