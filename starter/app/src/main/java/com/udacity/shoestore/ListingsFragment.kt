package com.udacity.shoestore

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.udacity.shoestore.databinding.FragmentListingsBinding
import com.udacity.shoestore.models.Shoe

class ListingsFragment: Fragment() {

    private lateinit var binding: FragmentListingsBinding
    private lateinit var viewModel: ShoeViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_listings, container, false)
        binding.addShoeButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listingsFragment_to_addShoeFragment))
        binding.setLifecycleOwner(this)

        binding.logoutButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listingsFragment_to_loginFragment))

        viewModel = ViewModelProvider(requireActivity()).get(ShoeViewModel::class.java)
        viewModel.shoes.observe(viewLifecycleOwner, Observer { newShoeList ->
            addNewShoe(newShoeList)
        })

        // From developer documentation, since onCreateOptionsMenu() is deprecated
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.logout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // This is a work-around, since giving the logout menu item id "action_listingsFragment_to_loginFragment"
                // and calling menuItem.onNavDestinationSelected() did not delete the back stack of the action (bug?)
                findNavController().navigate(R.id.action_listingsFragment_to_loginFragment)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    private fun addNewShoe(newShoeList: MutableList<Shoe>) {
        var shoe_listing : LinearLayout = binding.shoeListing

        for(shoe in newShoeList) {
            var newShoeTextView: TextView = TextView(context)
            newShoeTextView.text = "Name: ${shoe.name}, Size: ${shoe.size}, Brand: ${shoe.company}, Description: ${shoe.description}"
            newShoeTextView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

            shoe_listing.addView(newShoeTextView)
            shoe_listing.size
        }
    }

}