package com.udacity.shoestore

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentListingsBinding
import com.udacity.shoestore.models.Shoe
import timber.log.Timber


class ListingsFragment: Fragment() {

    private lateinit var binding: FragmentListingsBinding
    private lateinit var viewModel: ShoeViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("ListingFragment beginning of onCreateView")
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_listings, container, false)
        binding.addShoeButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_listingsFragment_to_addShoeFragment))
        //binding.setLifecycleOwner(this)

        viewModel = ViewModelProvider(requireActivity()).get(ShoeViewModel::class.java)
        viewModel.shoes.observe(viewLifecycleOwner, Observer { newShoeList ->
            Timber.i("ListingFragment observer triggered")
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
        Timber.i("ListingFragment end of onCreateView")
        return binding.root
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }

    private fun addNewShoe(newShoeList: MutableList<Shoe>) {
        var shoe_listing : LinearLayout = binding.shoeListing
        for(shoe in newShoeList) {
            // ConstraintLayout shoe_entry
            val constraintLayout = ConstraintLayout(requireContext())
            constraintLayout.id = View.generateViewId()
            val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            val margin = resources.getDimensionPixelSize(R.dimen.small_margin)
            layoutParams.setMargins(margin)
            constraintLayout.layoutParams = layoutParams
            constraintLayout.background = ContextCompat.getDrawable(requireContext(), R.drawable.textview_border)


            // ImageView shoe_image
            val shoeImage = ImageView(context)
            shoeImage.setImageResource(R.drawable.sneaker_shoe_svgrepo_com)
            shoeImage.setPadding(resources.getDimensionPixelSize(R.dimen.tiny_padding))
            shoeImage.background = ContextCompat.getDrawable(requireContext(), R.drawable.textview_border)
            shoeImage.id = View.generateViewId()
            val shoeImageLayoutParams = ConstraintLayout.LayoutParams(
                218,
                218
            )
            shoeImageLayoutParams.marginStart = dpToPx(16f)
            shoeImageLayoutParams.topMargin = dpToPx(16f)
            shoeImageLayoutParams.bottomMargin = dpToPx(16f)
            shoeImage.layoutParams = shoeImageLayoutParams
            constraintLayout.addView(shoeImage)

            val imageConstraints = ConstraintSet()
            imageConstraints.clone(constraintLayout)
            imageConstraints.connect(shoeImage.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            imageConstraints.connect(shoeImage.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            imageConstraints.connect(shoeImage.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            imageConstraints.applyTo(constraintLayout)


            // TextViews
            val fontTypeface = resources.getFont(R.font.wellfleet)

            // TextView shoe_name
            val shoeName = TextView(context)
            shoeName.id = View.generateViewId()
            shoeName.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            shoeName.text = shoe.name
            shoeName.typeface = fontTypeface
            shoeName.textSize = 20f
            shoeName.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            constraintLayout.addView(shoeName)


            // TextView shoe_size
            val shoeSize = TextView(context)
            shoeSize.id = View.generateViewId()
            shoeSize.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            shoeSize.text = "Size ${shoe.size}"
            shoeSize.typeface = fontTypeface
            shoeSize.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            constraintLayout.addView(shoeSize)

            // TextView shoe_company
            val shoeCompany = TextView(context)
            shoeCompany.id = View.generateViewId()
            shoeCompany.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            shoeCompany.text = "Brand: ${shoe.company}"
            shoeCompany.typeface = fontTypeface
            shoeCompany.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            constraintLayout.addView(shoeCompany)


            // TextView shoe_description
            val shoeDescription = TextView(context)
            shoeDescription.id = View.generateViewId()
            val shoeDescriptionLayoutParams = ConstraintLayout.LayoutParams(
                0,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )

            shoeDescriptionLayoutParams.marginStart = dpToPx(16f)
            shoeDescriptionLayoutParams.marginEnd = dpToPx(16f)
            shoeDescription.layoutParams =shoeDescriptionLayoutParams

            shoeDescription.background = ContextCompat.getDrawable(requireContext(), R.drawable.textview_border)
            shoeDescription.typeface = fontTypeface
            shoeDescription.text = "${shoe.description}"
            shoeDescription.maxLines = 2
            shoeDescription.movementMethod = ScrollingMovementMethod()
            shoeDescription.isVerticalScrollBarEnabled = true
            shoeDescription.setPadding(resources.getDimensionPixelSize(R.dimen.tiny_padding))
            constraintLayout.addView(shoeDescription)


            // Text View Constraints
            val textConstraints = ConstraintSet()
            textConstraints.clone(constraintLayout)


            // Constraints TextView shoe_size
            textConstraints.connect(shoeName.id, ConstraintSet.START, shoeImage.id, ConstraintSet.END)
            textConstraints.connect(shoeName.id, ConstraintSet.TOP, shoeImage.id, ConstraintSet.TOP)
            textConstraints.connect(shoeName.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            textConstraints.connect(shoeName.id, ConstraintSet.BOTTOM, shoeCompany.id, ConstraintSet.TOP)

            textConstraints.connect(shoeSize.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            textConstraints.setHorizontalBias(shoeSize.id, 0.5f)
            textConstraints.connect(shoeSize.id, ConstraintSet.START, shoeCompany.id, ConstraintSet.END)
            textConstraints.connect(shoeSize.id, ConstraintSet.BASELINE, shoeCompany.id, ConstraintSet.BASELINE)

            textConstraints.connect(shoeCompany.id, ConstraintSet.BOTTOM, shoeDescription.id, ConstraintSet.TOP)
            textConstraints.connect(shoeCompany.id, ConstraintSet.END, shoeSize.id, ConstraintSet.START)
            textConstraints.setHorizontalBias(shoeCompany.id, 0.5f)
            textConstraints.connect(shoeCompany.id, ConstraintSet.START, shoeImage.id, ConstraintSet.END)
            textConstraints.connect(shoeCompany.id, ConstraintSet.TOP, shoeName.id, ConstraintSet.BOTTOM)

            textConstraints.connect(shoeDescription.id, ConstraintSet.BOTTOM, shoeImage.id, ConstraintSet.BOTTOM)
            textConstraints.connect(shoeDescription.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            textConstraints.connect(shoeDescription.id, ConstraintSet.START, shoeImage.id, ConstraintSet.END, margin)
            textConstraints.connect(shoeDescription.id, ConstraintSet.TOP, shoeCompany.id, ConstraintSet.BOTTOM)

            textConstraints.applyTo(constraintLayout)
            Timber.i("Finished creating constrained layout")

            /*var newShoeTextView: TextView = TextView(context)
            newShoeTextView.text = "Name: ${shoe.name}, Size: ${shoe.size}, Brand: ${shoe.company}, Description: ${shoe.description}"
            newShoeTextView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            */
            shoe_listing.addView(constraintLayout)
            Timber.i("Added constrained layout to listing")
        }
    }

}