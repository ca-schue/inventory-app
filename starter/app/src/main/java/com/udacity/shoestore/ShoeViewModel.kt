package com.udacity.shoestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber

class ShoeViewModel : ViewModel() {

    private val _shoes = MutableLiveData<MutableList<Shoe>>()
    val shoes : LiveData<MutableList<Shoe>>
        get() = _shoes

    init {
        _shoes.value = mutableListOf<Shoe>()
    }

    fun addShoe(name: String, size: Double, company: String, description: String) {
        _shoes.value!!.add(0, Shoe(name, size, company, description))
        Timber.i("Added shoe to list: ${_shoes.value}")
    }
}