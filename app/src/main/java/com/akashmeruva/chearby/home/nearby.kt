package com.akashmeruva.chearby.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akashmeruva.chearby.R
import com.akashmeruva.chearby.databinding.FragmentNearbyBinding

class nearby : Fragment(R.layout.fragment_nearby) {

    private lateinit var binding : FragmentNearbyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNearbyBinding.bind(view)

    }
}