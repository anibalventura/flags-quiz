package com.anibalventura.flagsquiz.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Calculator class
@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    private class HistoryDatabaseCallback : RoomDatabase.Callback()

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(
            context: Context
        ): HistoryDatabase {
            // If the INSTANCE is not null, then return it, if it is, then create the database.
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java,
                        "history_database"
                    )
                        .addCallback(
                            HistoryDatabaseCallback()
                        )
                        .fallbackToDestructiveMigration() // Gracefully handle missing migration paths.
                        .build()
                    INSTANCE = instance
                    // Return instance.
                    instance
                }
        }
    }
}