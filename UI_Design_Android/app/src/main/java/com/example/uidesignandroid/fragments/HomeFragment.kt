package com.example.uidesignandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uidesignandroid.adapters.LocationsAdapter
import com.example.uidesignandroid.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val locations = arrayListOf("Home", "Office", "Hospitals", "Fitness")
        val adapter = LocationsAdapter(locations)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLocations.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }
        return binding.root
    }
}