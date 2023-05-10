package com.akashmeruva.chearby.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akashmeruva.chearby.R
import com.akashmeruva.chearby.databinding.FragmentRandomBinding

class random : Fragment(R.layout.fragment_random) {

    private  lateinit var binding : FragmentRandomBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRandomBinding.bind(view)
    }
}