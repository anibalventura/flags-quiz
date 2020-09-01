package com.anibalventura.flagsquiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Initialize DataBiding for this activity.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use DataBinding to set the activity view.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Setup navigation.
        setupNavigation()
    }

    /**
     * Called when the hamburger menu or back button are pressed on the Toolbar.
     *
     * Delegate this to Navigation.
     */
    override fun onSupportNavigateUp() =
        navigateUp(findNavController(R.id.navHostFragment), binding.drawerLayout)

    /**
     * Setup Navigation for this Activity.
     */
    private fun setupNavigation() {
        // Set the toolbar.
        setSupportActionBar(binding.toolbar)

        // Find the nav controller.
        val navController = findNavController(R.id.navHostFragment)

        // Setup the action bar, tell it about the DrawerLayout.
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // Setup the left drawer (called a NavigationView).
        binding.navigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when (destination.id) {
                R.id.homeFragment -> {
                    toolBar.setDisplayShowTitleEnabled(false)
                }
                else -> {
                    toolBar.setDisplayShowTitleEnabled(true)
                }
            }
        }
    }
}