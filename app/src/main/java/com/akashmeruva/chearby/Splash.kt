package com.akashmeruva.chearby

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.akashmeruva.chearby.databinding.FragmentSplashBinding

class Splash : Fragment(R.layout.fragment_splash) {

    lateinit var binding : FragmentSplashBinding
    private lateinit var sp : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = requireActivity().getSharedPreferences("userdetails", Context.MODE_PRIVATE)
        binding = FragmentSplashBinding.inflate(layoutInflater)
        val navController = findNavController()
        val value = sp.getString("loginprocess", "")

        if(value == "shouldRegister")
        {
            Handler().postDelayed(
                {
                    navController.navigate(R.id.action_splash_to_register)
                }, 1500)
        }else if(value == "done")
        {
            Handler().postDelayed(
                {
                    navController.navigate(R.id.action_splash_to_home2)
                    requireActivity().finish()
                }, 1500)
        }else
        {
            Handler().postDelayed(
                {
                    navController.navigate(R.id.action_splash_to_login)
                }, 1500)
        }
    }



}