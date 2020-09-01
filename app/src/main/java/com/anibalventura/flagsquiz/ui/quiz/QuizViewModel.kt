package com.anibalventura.flagsquiz.ui.quiz

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class QuizViewModel(private val contextApp: Context, application: Application) :
    AndroidViewModel(application) {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName
}