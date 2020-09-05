package com.anibalventura.flagsquiz

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.widget.Toast

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }

    companion object {
        /*
         * Get app resourses anywhere in the app.
         */
        var instance: App? = null
            private set
        var resourses: Resources? = null
            private set

        /*
         * Show Toast.
         */
        fun Context.showToast(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}