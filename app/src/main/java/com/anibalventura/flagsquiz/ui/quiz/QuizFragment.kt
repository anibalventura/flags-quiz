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

    // Use DataBinding.
    private lateinit var binding: FragmentQuizBinding

    // Questions from database.
    private var questionList: ArrayList<Question>? = null

    // Quiz.
    private var currentPosition: Int = 1 // Default and the first question position
    private var selectedOptionPosition: Int = 0
    private var correctAnswers: Int = 0
    private var submitQuestion: Boolean = false

    // Lives count.
    private var lives: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        /**
         * Set ViewModel and ViewModelFactory.
         */
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

        // Get the selected category.
        questionList = CONSTANTS.getQuestions()

        // Set the current question.
        setQuestion()

        // get option selected.
        selectedOptionsView(binding.tvOptionOne, 1)
        selectedOptionsView(binding.tvOptionTwo, 2)
        selectedOptionsView(binding.tvOptionThree, 3)
        selectedOptionsView(binding.tvOptionFour, 4)

        // Submit the answer.
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

        // Reset the view to default.
        defaultOptionsView()

        // Set the progress
        binding.progressBar.progress = currentPosition
        binding.tvProgressBar.text =
            getString(R.string.progress, currentPosition, binding.progressBar.max)

        // Set the view to the current question.
        binding.tvQuestion.text = question.question
        binding.ivFlag.setImageResource(question.image)
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour

        // Enable to select an option.
        binding.tvOptionOne.isClickable = true
        binding.tvOptionTwo.isClickable = true
        binding.tvOptionThree.isClickable = true
        binding.tvOptionFour.isClickable = true

        // Reset the button to "Submit".
        binding.btnSubmit.text = getString(R.string.btn_submit)

        // Cannot submit question if a option is not selected.
        submitQuestion = false
    }

    /*
     * Set the view of options to default.
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
     * Set the option view to selected.
     */
    private fun selectedOptionsView(tv: TextView, selectionOptionNum: Int) {
        tv.setOnClickListener {
            defaultOptionsView()

            // Get the selected option.
            selectedOptionPosition = selectionOptionNum

            // When option is selected can submit the question.
            submitQuestion = true

            // Set the option view to selected.
            tv.setTextColor((Color.parseColor("#363A43")))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.selected_option_border_bg
            )
        }
    }

    /*
     * Highlight the answer for wrong or right.
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

    /*
     * Submit the answer.
     */
    private fun submitAnswer(view: View) {
        when {
            // Show a toast if trying to submit question without an option.
            !submitQuestion -> Toast.makeText(
                requireContext(),
                getString(R.string.please_select_option),
                Toast.LENGTH_SHORT
            ).show()

            // When pass to another question.
            selectedOptionPosition == 0 -> {
                currentPosition++

                when {
                    currentPosition <= questionList!!.size -> setQuestion()
                    else -> view.findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToWonFragment(
                            correctAnswers,
                            questionList!!.size
                        )
                    )
                }
            }

            // Show loseFragment when lives get to 0.
            lives == 0 -> view.findNavController()
                .navigate(QuizFragmentDirections.actionQuizFragmentToLoseFragment())

            // When option selected check for correct or wrong answer.
            else -> {
                // Get question.
                val question = questionList?.get(currentPosition - 1)

                // If option wrong.
                if (question!!.correctAnswer != selectedOptionPosition) {
                    // Mark selected view to wrong.
                    answerView(selectedOptionPosition, R.drawable.wrong_option_border_bg)

                    // Decrease lives.
                    lives--
                    when (lives) {
                        1 -> binding.ivLiveOne.visibility = View.GONE
                        0 -> binding.ivLiveTwo.visibility = View.GONE
                    }
                } else {
                    correctAnswers++
                }

                // Always check for correct answer.
                answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                // When submit answer cannot change options.
                binding.tvOptionOne.isClickable = false
                binding.tvOptionTwo.isClickable = false
                binding.tvOptionThree.isClickable = false
                binding.tvOptionFour.isClickable = false

                // When the questions are over, button Submit change to finish.
                if (currentPosition == questionList!!.size) {
                    binding.btnSubmit.text = getString(R.string.btn_finish)
                } else {
                    binding.btnSubmit.text = getString(R.string.btn_submit)
                }

                // Reset selected option for pass to another question.
                selectedOptionPosition = 0
            }
        }
    }
}