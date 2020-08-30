package com.anibalventura.flagsquiz

import android.app.Application
import android.content.res.Resources

/*
 * Get app resourses anywhere in the app.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        resourses = resources
    }

    companion object {
        var instance: App? = null
            private set
        var resourses: Resources? = null
            private set
    }
}