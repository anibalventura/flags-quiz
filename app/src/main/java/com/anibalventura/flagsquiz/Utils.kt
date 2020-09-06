package com.anibalventura.flagsquiz

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.anibalventura.flagsquiz.data.local.CONST

class Utils : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }

    companion object {
        /*
         * Get app resourses anywhere in the app.
         */
        var instance: Utils? = null
            private set
        var resourses: Resources? = null
            private set

        /*
         * Show Toast.
         */
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        /*
         * Setup the theme.
         */
        fun setTheme() {
            // Specify the context of the fragment for the shared preferences.
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(instance)
            // Set the theme from the sharedPref value.
            when (sharedPref.getString(CONST.THEME, "0")) {
                "1" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "2" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "0" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        /*
         * SharedPreferences.
         */
        fun sharedPref(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }
    }
}