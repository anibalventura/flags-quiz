package com.anibalventura.flagsquiz.data.local.db

/*
 * All the data for every question in the quiz.
 */
data class Question(
    val image: Int,
    var answers: List<String>
)