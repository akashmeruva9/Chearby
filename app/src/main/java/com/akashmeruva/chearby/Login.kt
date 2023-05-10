package com.akashmeruva.chearby

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.akashmeruva.chearby.databinding.FragmentLoginBinding

class Login : Fragment(R.layout.fragment_login) {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var sp : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = requireActivity().getSharedPreferences("userdetails", Context.MODE_PRIVATE)

        binding = FragmentLoginBinding.inflate(layoutInflater)

        val navController = findNavController()

        val btn = view.findViewById<Button>(R.id.get_start_btn)

        btn.setOnClickListener {
            navController.navigate(R.id.action_login_to_otp_auth)
        }

    }
}