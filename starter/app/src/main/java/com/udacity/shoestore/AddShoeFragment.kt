package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentAddShoeBinding

class AddShoeFragment: Fragment() {

    private lateinit var binding: FragmentAddShoeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_shoe, container, false)

        binding.saveShoeButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addShoeFragment_to_listingsFragment_save))
        binding.cancelButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addShoeFragment_to_listingsFragment_cancel))

        return binding.root
    }
}