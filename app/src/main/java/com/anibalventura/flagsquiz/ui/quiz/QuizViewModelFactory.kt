package com.anibalventura.flagsquiz.ui.quiz

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Simple ViewModel factory that provides the context to the ViewModel.
 */
class QuizViewModelFactory(
    private val contextApp: Context,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(contextApp, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}