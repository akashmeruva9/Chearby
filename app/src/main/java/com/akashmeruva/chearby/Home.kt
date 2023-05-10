package com.akashmeruva.chearby

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.akashmeruva.chearby.databinding.ActivityHomeBinding
import com.akashmeruva.chearby.home.encrypted
import com.akashmeruva.chearby.home.nearby
import com.akashmeruva.chearby.home.profile
import com.akashmeruva.chearby.home.random
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.homeBottomNavigationBar.selectedItemId = R.id.encrypted_bottom_nav
        replacefragment(encrypted())

        binding.homeBottomNavigationBar.setOnItemSelectedListener { item ->

            when (item.itemId)
            {
                R.id.nearby_bottom_nav -> {
                    replacefragment(nearby())
                }
                R.id.random_bottom_nav -> {
                    replacefragment(random())
                }

                R.id.profile_bottom_nav -> {
                    replacefragment(profile())
                }

                R.id.encrypted_bottom_nav -> {
                    replacefragment(encrypted())
                }
            }

            return@setOnItemSelectedListener true
        }

    }

    private fun replacefragment(fragment : Fragment)
    {
        val fragmentmanager = supportFragmentManager
        val fragmentTransaction = fragmentmanager.beginTransaction()
        fragmentTransaction.replace(R.id.Home_Activity_Fragment_Container, fragment)
        fragmentTransaction.commit()
    }
}