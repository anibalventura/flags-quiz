package com.anibalventura.flagsquiz.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.anibalventura.flagsquiz.data.local.db.History
import com.anibalventura.flagsquiz.data.local.db.HistoryDatabase
import com.anibalventura.flagsquiz.data.local.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HistoryRepository

    /**
     * Using LiveData and caching what getHistory returns has several benefits:
     * 1. Put an observer on the data (instead of polling for changes) and only update the
     *    the UI when the data actually changes.
     * 2. Repository is completely separated from the UI through the ViewModel.
     */
    val allHistory: LiveData<List<History>>

    init {
        val historyDao =
            HistoryDatabase.getDatabase(application).historyDao()
        repository =
            HistoryRepository(
                historyDao
            )
        allHistory = repository.allHistory
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way.
     */
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}