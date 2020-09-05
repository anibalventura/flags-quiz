package com.anibalventura.flagsquiz.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anibalventura.flagsquiz.App.Companion.showToast
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.sharedpref.CONST
import com.anibalventura.flagsquiz.data.local.sharedpref.SharedPreferences
import com.anibalventura.flagsquiz.databinding.ActivityWelcomeBinding
import com.anibalventura.flagsquiz.ui.MainActivity


class WelcomeActivity : AppCompatActivity() {

    // Use DataBinding.
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Set theme after splash screen.
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        // Use DataBinding to set the activity view.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        // Press continue button.
        binding.btnContinue.setOnClickListener { continueToHome() }
    }

    /**
     * Set the behavior when press Continue button.
     */
    private fun continueToHome() {
        // Show a Toast message when don't have a user name.
        if (binding.etName.text.toString().isEmpty()) {
            showToast(getString(R.string.welcome_enter_name))
        } else {
            // Initialize SharedPreferences.
            val sharedPref = SharedPreferences(this)
            // Get and save the userName on sharedPref.
            val userName = binding.etName.text.toString()
            sharedPref.putString(CONST.USER_NAME, userName)

            // Start the MainActivity and finish the WelcomeActivity.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            // Don't show more this activity.
            sharedPref.putBoolean(CONST.START_ACT, true)
        }
    }
}