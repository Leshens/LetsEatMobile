package com.leshen.letseatmobile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.leshen.letseatmobile.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private var binding:FragmentProfileBinding?= null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding?.signOutButton1?.setOnClickListener {
            (activity as MainActivity).signOut(auth)

        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding?.signOutButton1?.setOnClickListener {
            (activity as MainActivity).signOut(auth)

        }



        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}