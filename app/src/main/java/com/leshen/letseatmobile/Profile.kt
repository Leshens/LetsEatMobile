package com.leshen.letseatmobile


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.leshen.letseatmobile.databinding.FragmentProfileBinding

class Profile : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private var userEmail: String? = null
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        userEmail = auth.currentUser?.email
        username = userEmail?.substringBefore('@') ?: ""
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val myTextView: TextView = view.findViewById(R.id.helloText)
        myTextView.text = "Hej, $username"


        return view
    }
}

