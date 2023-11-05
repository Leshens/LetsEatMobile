package com.leshen.letseatmobile


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.leshen.letseatmobile.databinding.FragmentProfileBinding
import com.leshen.letseatmobile.login.GetStartedActivity

class Profile : Fragment() {
    private var binding:FragmentProfileBinding?= null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding?.signOutButton?.setOnClickListener {
            if (auth.currentUser!= null) {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity, GetStartedActivity::class.java)
                startActivity(intent)

            }

        }



        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}