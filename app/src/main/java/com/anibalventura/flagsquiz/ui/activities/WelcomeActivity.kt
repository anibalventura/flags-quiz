package com.anibalventura.flagsquiz.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.data.local.CONST
import com.anibalventura.flagsquiz.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    // Use DataBinding.
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use DataBinding to set the activity view.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        // If name is enter, continue to HomeFragment.
        binding.btnContinue.setOnClickListener { continueToHome() }

        // Skip to enter a name.
        binding.tvSkip.setOnClickListener { disableWelcome() }
    }

    /**
     * Set the behavior when press Continue button.
     */
    private fun continueToHome() {
        // Show a Toast message when don't have a user name.
        if (binding.etName.text.toString().isEmpty()) {
            Utils.showToast(this, getString(R.string.welcome_enter_name))
        } else {
            // Get name input.
            val userName = binding.etName.text.toString()
            // Update name input on sharedPreferences.
            Utils.sharedPref(this).edit().putString(CONST.USER_NAME, userName).apply()
            // Not show again this activity.
            disableWelcome()
            // Finish WelcomeActivity and go to MainActivity.
            finish()
        }
    }

    /*
     * Not show again this Activity.
     */
    private fun disableWelcome() {
        // Update SharedPreferences to not show again this activity.
        Utils.sharedPref(this).edit().putBoolean(CONST.SHOW_ACT, true).apply()
        // Finish WelcomeActivity and go to MainActivity.
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        disableWelcome()
    }
}