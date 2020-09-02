package com.anibalventura.flagsquiz.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.SharedPreferences
import com.anibalventura.flagsquiz.databinding.ActivityWelcomeBinding
import com.anibalventura.flagsquiz.ui.MainActivity

class WelcomeActivity : AppCompatActivity() {

    // Use DataBinding.
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use DataBinding to set the activity view.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // Initialize SharedPreferences.
        val sharedPreferences = SharedPreferences(this)

        /**
         * Set the behavior when press Continue button.
         */
        binding.btnContinue.setOnClickListener {
            // Show a Toast message when don't have a user name.
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Get and save the userName on sharedPreferences.
                val userName = binding.etName.text.toString()
                sharedPreferences.putString("user_name", userName)

                // Start the MainActivity and finish the WelcomeActivity.
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}