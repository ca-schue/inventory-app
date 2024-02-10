package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentListingsBinding

class ListingsFragment: Fragment() {
    private lateinit var binding: FragmentListingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_listings, container, false)

        binding.addShoeButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listingsFragment_to_addShoeFragment))

        return binding.root    }
}