package com.anibalventura.flagsquiz.data.local.sharedpref

import android.content.Context

class SharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(CONST.PREFERENCES, 0)

    /**
     * Boolean values
     */
    //Save boolean values into the shared preferences
    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    // Retrieve boolean values from the shared preferences
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    /**
     * String values
     */
    //Save string values into the shared preferences
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    // Retrieve string values from the shared preferences
    fun getString(key: String, defValue: String): String? {
        return sharedPreferences.getString(key, defValue)
    }

    /**
     * Int values
     */
    //Save boolean values into the shared preferences
    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    // Retrieve boolean values from the shared preferences
    fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }
}