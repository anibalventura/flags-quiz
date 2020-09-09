package com.anibalventura.flagsquiz.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A basic class representing an entity that is a row in a two-column database table.
 *
 * @ Entity - Annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - Identify the primary key.
 * @ ColumnInfo - Supply the column name if it is different from the variable name.
 */
@Entity(tableName = "quiz_history_database")
class History(
    @ColumnInfo(name = "result")
    val result: String,

    @ColumnInfo(name = "correct_answers")
    val correctAnswers: Int,

    @ColumnInfo(name = "total_answers")
    val totalAnswers: Int,

    @ColumnInfo(name = "continent")
    val continent: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}