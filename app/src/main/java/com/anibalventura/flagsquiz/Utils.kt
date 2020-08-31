package com.anibalventura.flagsquiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class Utils(application: Application) : AndroidViewModel(application) {
    /**
     * Custom getString for ViewModel.
     */
    fun getString(
        string: Int,
        value1: String = "0",
        value2: String = "0",
        value3: String = "0",
        value4: String = "0",
        value5: String = "0",
        value6: String = "0",
        value7: String = "0"
    ): String {

        return getApplication<Application>().resources.getString(
            string,
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7
        )
    }
}