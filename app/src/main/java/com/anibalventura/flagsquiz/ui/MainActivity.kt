package com.anibalventura.flagsquiz.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anibalventura.flagsquiz.CONST
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.Utils.Companion.sharedPref
import com.anibalventura.flagsquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Use DataBinding.
    private lateinit var binding: ActivityMainBinding

    // NavController.
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set theme after splash screen.
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        // Use DataBinding to set the activity view.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Show WelcomeActivity for the first time.
        startWelcome()

        // Setup navigation.
        setupNavigation()

        // Setup theme.
        Utils.setupTheme(this)
    }

    /*
     * Start the WelcomeActivity when the user install the app.
     */
    private fun startWelcome() {
        when {
            // Get status from SharedPreferences.
            !sharedPref(this).getBoolean(CONST.SHOW_ACT, false) -> {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Called when the hamburger menu or back button are pressed on the Toolbar.
     * Delegate this to Navigation.
     */
    override fun onSupportNavigateUp() = navigateUp(
        findNavController(R.id.navHostFragment), binding.drawerLayout
    )

    /**
     * Setup Navigation for this Activity.
     */
    private fun setupNavigation() {
        // Set the toolbar.
        setSupportActionBar(binding.toolbar)

        // Find the nav controller.
        navController = findNavController(R.id.navHostFragment)

        // Setup the action bar, tell it about the DrawerLayout.
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // Setup the left drawer (called a NavigationView).
        binding.navigationView.setupWithNavController(navController)
    }
}

