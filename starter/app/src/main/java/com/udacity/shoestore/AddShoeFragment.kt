package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentAddShoeBinding
import timber.log.Timber

class AddShoeFragment: Fragment() {

    private lateinit var binding: FragmentAddShoeBinding
    private lateinit var viewModel: ShoeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_shoe, container, false)

        binding.saveShoeButton.setOnClickListener{
            addNewShoe()
            Navigation.findNavController(it).navigate(R.id.action_addShoeFragment_to_listingsFragment_save)
        }
        binding.cancelButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addShoeFragment_to_listingsFragment_cancel))

        viewModel = ViewModelProvider(requireActivity()).get(ShoeViewModel::class.java)

        return binding.root
    }

    private fun addNewShoe() {
        var name : String = binding.shoeNameEditText.text.toString()
        var size : Double = binding.shoeSizeEditText.text.toString().toDouble()
        var company : String = binding.shoeCompanyEditText.text.toString()
        var description : String = binding.shoeDescriptionText.text.toString()

        viewModel.addShoe(name, size, company, description)
    }
}