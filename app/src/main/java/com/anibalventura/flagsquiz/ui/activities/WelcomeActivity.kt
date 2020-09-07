package com.anibalventura.flagsquiz.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.Utils.Companion.showToast
import com.anibalventura.flagsquiz.data.local.CONST
import com.anibalventura.flagsquiz.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    // Use DataBinding.
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
            showToast(this, getString(R.string.welcome_enter_name))
        } else {
            // Get name input.
            val userName = binding.etName.text.toString()

            // Put name input on sharedPref.
            Utils.sharedPref(this).edit().putString(CONST.USER_NAME, userName).apply()
            // Update SharedPreferences to not show again this activity.
            Utils.sharedPref(this).edit().putBoolean(CONST.SHOW_ACT, true).apply()

            // Finish WelcomeActivity to start the MainActivity.
            finish()
        }
    }
}