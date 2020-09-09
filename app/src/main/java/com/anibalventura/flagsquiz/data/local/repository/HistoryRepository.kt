package com.anibalventura.flagsquiz.data.local.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.anibalventura.flagsquiz.data.local.db.History
import com.anibalventura.flagsquiz.data.local.db.HistoryDao

/**
 * Declares the DAO as a private property in the constructor. Pass in the DAO
 * instead of the whole database, because we only need access to the DAO.
 */
class HistoryRepository(private val historyDao: HistoryDao) {

    /**
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     */
    val allHistory: LiveData<List<History>> = historyDao.getHistory()

    /**
     * Must call this on a non-UI thread or the app will crash. So this is a
     * suspend function so the caller methods know this. Like this, Room ensures that
     * not doing any long running operations on the main thread, blocking the UI.
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(history: History) {
        historyDao.insert(history)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        historyDao.deleteAll()
    }
}