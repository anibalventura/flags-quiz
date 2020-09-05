package com.anibalventura.flagsquiz.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.sharedpref.CONST
import com.anibalventura.flagsquiz.data.local.sharedpref.SharedPreferences
import com.anibalventura.flagsquiz.databinding.ActivityMainBinding
import com.anibalventura.flagsquiz.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    // Use DataBinding.
    private lateinit var binding: ActivityMainBinding

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
        setTheme()
    }

    /*
     * Start the WelcomeActivity when the user install the app.
     */
    private fun startWelcome() {
        // Initialize SharedPreferences.
        val sharedPref = SharedPreferences(this)
        when {
            !sharedPref.getBoolean(CONST.START_ACT, false) -> {
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
        val navController = findNavController(R.id.navHostFragment)

        // Setup the action bar, tell it about the DrawerLayout.
        setupActionBarWithNavController(navController, binding.drawerLayout)

        // Setup the left drawer (called a NavigationView).
        binding.navigationView.setupWithNavController(navController)
    }

    /*
     * Setup the theme.
     */
    private fun setTheme() {
        // Specify the context of the fragment for the shared preferences.
        val sharedPreferences = SharedPreferences(this)
        // Set the theme from the sharedPreferences value.
        when (sharedPreferences.getInt(CONST.THEME, 0)) {
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}