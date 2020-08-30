package com.anibalventura.flagsquiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.CONSTANTS

class QuizQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        val questionList = CONSTANTS.getQuestions()

    }
}