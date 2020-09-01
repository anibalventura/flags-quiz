package com.anibalventura.flagsquiz.data.local.db

/*
 * All the data for every question in the quiz.
 */
data class Question(
    val question: String,
    val image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int,
)