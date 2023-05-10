package com.akashmeruva.chearby.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akashmeruva.chearby.MainActivity
import com.akashmeruva.chearby.R
import com.akashmeruva.chearby.databinding.FragmentEncryptedBinding
import com.akashmeruva.chearby.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class profile : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.signOutBtn.setOnClickListener {
            signout()
        }
    }

    fun signout()
    {
        var mAuth: FirebaseAuth? = null
        lateinit var sp : SharedPreferences
        sp = requireContext().getSharedPreferences("userdetails" , Context.MODE_PRIVATE)
        mAuth = FirebaseAuth.getInstance()
        mAuth!!.signOut()
        val editor = sp.edit()
        editor.putString("loginprocess" , "")
        editor.commit()
        val intent = Intent(context , MainActivity::class.java)
        startActivity(intent)
    }
}