package com.anibalventura.flagsquiz.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Room Magic is in this file, where map a method call to an SQL query.
 */
@Dao
interface HistoryDao {

    /**
     * LiveData is a data holder class that can be observed within a given lifecycle.
     * Always holds/caches latest version of data. Notifies its active observers when the
     * data has changed. Since we are getting all the contents of the database,
     * we are notified whenever any of the database contents have changed.
     */
    @Query("SELECT * from quiz_history_database ORDER BY id DESC")
    fun getHistory(): LiveData<List<History>>

    // Insert data to the database.
    // onConflict is used to ignore when there are the same expressions.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: History)

    // Delete all the data from the database.
    @Query("DELETE FROM quiz_history_database")
    suspend fun deleteAll()
}