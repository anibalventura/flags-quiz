package com.anibalventura.flagsquiz.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.CONSTANTS
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Hide the notification bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val username = intent.getStringExtra(CONSTANTS.USER_NAME)
        tvResultUsername.text = username

        val correctAnswers = intent.getIntExtra(CONSTANTS.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(CONSTANTS.TOTAL_QUESTIONS, 0)
        tvResultScore.text = getString(R.string.total_score, correctAnswers, totalQuestions)

        btnResultFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}