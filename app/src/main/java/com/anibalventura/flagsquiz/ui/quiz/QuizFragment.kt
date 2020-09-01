package com.anibalventura.flagsquiz.ui.quiz

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.db.CONSTANTS
import com.anibalventura.flagsquiz.data.local.db.Question
import com.anibalventura.flagsquiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var currentPosition: Int = 1 // Default and the first question position
    private var questionList: ArrayList<Question>? = null
    private var selectedOptionPosition: Int = 0
    private var correctAnswers: Int = 0
    private var lives: Int = 2
    private var next: Boolean = false

    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        // Set ViewModel and ViewModelFactory
        val viewModelFactory =
            QuizViewModelFactory(requireContext(), requireNotNull(activity).application)
        val viewModel: QuizViewModel =
            ViewModelProvider(this, viewModelFactory).get(QuizViewModel::class.java)

        /**
         * Set the viewModel for DataBinding - this allows the bound layout access
         * to all the data in the ViewModel
         */
        binding.quizViewModel = viewModel

        /**
         * Specify the fragment view as the lifecycle owner of the binding.
         * This is used so that the binding can observe LiveData updates
         */
        binding.lifecycleOwner = viewLifecycleOwner


        questionList = CONSTANTS.getQuestions()
        setQuestion()

        selectedOptionsView(binding.tvOptionOne, 1)
        selectedOptionsView(binding.tvOptionTwo, 2)
        selectedOptionsView(binding.tvOptionThree, 3)
        selectedOptionsView(binding.tvOptionFour, 4)

        binding.btnSubmit.setOnClickListener {
            submitAnswer(it)
        }

        return binding.root
    }

    /**
     * Setting the question to UI components.
     */
    private fun setQuestion() {
        // Getting the question from the list with the help of current position.
        val question = questionList!![currentPosition - 1]

        defaultOptionsView()

        binding.btnSubmit.text = getString(R.string.btn_submit)

        next = false

        binding.progressBar.progress = currentPosition
        binding.tvProgressBar.text =
            getString(R.string.progress, currentPosition, binding.progressBar.max)

        binding.tvQuestion.text = question.question
        binding.ivFlag.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        binding.tvOptionOne.isClickable = true
        binding.tvOptionTwo.isClickable = true
        binding.tvOptionThree.isClickable = true
        binding.tvOptionFour.isClickable = true
    }

    /*
     * Set the view of selected option view.
     */
    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options) {
            option.setTextColor((Color.parseColor("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.default_option_border_bg
            )
        }
    }

    /**
     * Set default options view when the new question is loaded or when the answer is reselected.
     */
    private fun selectedOptionsView(tv: TextView, selectionOptionNum: Int) {
        tv.setOnClickListener {
            defaultOptionsView()

            selectedOptionPosition = selectionOptionNum
            next = true

            tv.setTextColor((Color.parseColor("#363A43")))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.selected_option_border_bg
            )
        }
    }

    /*
     * Highlight the answer is wrong or right.
     */
    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> binding.tvOptionOne.background =
                ContextCompat.getDrawable(requireContext(), drawableView)
            2 -> binding.tvOptionTwo.background =
                ContextCompat.getDrawable(requireContext(), drawableView)
            3 -> binding.tvOptionThree.background =
                ContextCompat.getDrawable(requireContext(), drawableView)
            4 -> binding.tvOptionFour.background =
                ContextCompat.getDrawable(requireContext(), drawableView)
        }
    }

    private fun submitAnswer(view: View) {
        if (!next) {
            Toast.makeText(requireContext(), "Please select an option", Toast.LENGTH_SHORT).show()
        } else if (selectedOptionPosition == 0) {
            currentPosition++

            when {
                currentPosition <= questionList!!.size -> {
                    setQuestion()
                }
                else -> {
                    view.findNavController()
                        .navigate(
                            QuizFragmentDirections.actionQuizFragmentToWonFragment(
                                correctAnswers,
                                questionList!!.size
                            )
                        )
                }
            }
        } else if (lives == 0) {
            view.findNavController()
                .navigate(QuizFragmentDirections.actionQuizFragmentToLoseFragment())
        } else {
            val question = questionList?.get(currentPosition - 1)

            // Check if the answer is wrong
            if (question!!.correctAnswer != selectedOptionPosition) {
                answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                lives--
                when (lives) {
                    1 -> binding.ivLiveOne.visibility = View.GONE
                    0 -> binding.ivLiveTwo.visibility = View.GONE
                }
            } else {
                correctAnswers++
            }

            // Check for correct answer
            answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
            binding.tvOptionOne.isClickable = false
            binding.tvOptionTwo.isClickable = false
            binding.tvOptionThree.isClickable = false
            binding.tvOptionFour.isClickable = false

            if (currentPosition == questionList!!.size) {
                binding.btnSubmit.text = getString(R.string.btn_finish)
            } else {
                binding.btnSubmit.text = getString(R.string.btn_next_question)
            }

            selectedOptionPosition = 0
        }
    }
}














