package com.anibalventura.flagsquiz.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.CONSTANTS
import com.anibalventura.flagsquiz.data.Question
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity() {

    private var currentPosition: Int = 1
    private var questionList: ArrayList<Question>? = null
    private var userName: String? = null
    private var selectedOptionPosition: Int = 0
    private var correctAnswers: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(CONSTANTS.USER_NAME)

        questionList = CONSTANTS.getQuestions()

        setQuestion()

        selectedOptionsView(tvOptionOne, 1)
        selectedOptionsView(tvOptionTwo, 2)
        selectedOptionsView(tvOptionThree, 3)
        selectedOptionsView(tvOptionFour, 4)

        btnSubmit.setOnClickListener {
            submitAnswer()
        }
    }

    private fun setQuestion() {
        val question = questionList!![currentPosition - 1]

        defaultOptionsView()

        if (currentPosition == questionList!!.size) {
            btnSubmit.text = getString(R.string.btn_finish)
        } else {
            btnSubmit.text = getString(R.string.btn_submit)
        }

        progressBar.progress = currentPosition
        tvProgressBar.text = getString(R.string.progress, currentPosition, progressBar.max)

        tvQuestion.text = question.question
        ivFlag.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }

    /*
     * Set the default view when a item is selected.
     */
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (option in options) {
            option.setTextColor((Color.parseColor("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionsView(tv: TextView, selectionOptionNum: Int) {
        tv.setOnClickListener {
            defaultOptionsView()

            selectedOptionPosition = selectionOptionNum

            tv.setTextColor((Color.parseColor("#363A43")))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this, R.drawable.selected_option_border_bg
            )
        }
    }

    /*
     * Set the correct background on selected option.
     */
    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun submitAnswer() {
        if (selectedOptionPosition == 0) {
            currentPosition++

            when {
                currentPosition <= questionList!!.size -> {
                    setQuestion()
                }
                else -> {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra(CONSTANTS.USER_NAME, userName)
                    intent.putExtra(CONSTANTS.CORRECT_ANSWERS, correctAnswers)
                    intent.putExtra(CONSTANTS.TOTAL_QUESTIONS, questionList!!.size)
                    startActivity(intent)
                    finish()
                }
            }

        } else {
            val question = questionList?.get(currentPosition - 1)
            if (question!!.correctAnswer != selectedOptionPosition) {
                answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
            } else {
                correctAnswers++
            }
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

            if (currentPosition == questionList!!.size) {
                btnSubmit.text = getString(R.string.btn_finish)
            } else {
                btnSubmit.text = getString(R.string.btn_next_question)
            }

            selectedOptionPosition = 0
        }
    }
}














