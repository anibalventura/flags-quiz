package com.anibalventura.flagsquiz

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class Utils : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }

    companion object {
        /*
         * Get app resources anywhere in the app.
         */
        var instance: Utils? = null
            private set
        var resourses: Resources? = null
            private set

        /*
         * SharedPreferences.
         */
        fun sharedPref(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        /*
         * Show Toast.
         */
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        /*
         * Setup the current theme.
         */
        fun setupTheme(context: Context) {
            // Set the theme from the sharedPref value.
            when (sharedPref(context).getString(CONST.THEME, "0")) {
                "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "0" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}