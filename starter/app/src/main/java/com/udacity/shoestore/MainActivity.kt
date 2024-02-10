package com.udacity.shoestore

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
