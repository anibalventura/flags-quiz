package com.anibalventura.flagsquiz.ui.quiz

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.db.AMERICA
import com.anibalventura.flagsquiz.data.local.db.EUROPE
import com.anibalventura.flagsquiz.data.local.db.Question
import com.anibalventura.flagsquiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    // Use DataBinding.
    private lateinit var binding: FragmentQuizBinding

    /*
     * Quiz.
     */
    // Questions.
    private lateinit var questions: MutableList<Question> // Questions from database.
    private lateinit var currentQuestion: Question
    private var indexQuestion: Int = 0
    private var submitQuestion: Boolean = false
    private val numQuestions = 3

    // Answers.
    private lateinit var answers: MutableList<String>
    private lateinit var answersView: MutableList<TextView>
    private var indexAnswer: Int = 0
    private var correctAnswers: Int = 0

    // Lives count.
    private var lives: Int = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * Set ViewModel and ViewModelFactory.
         */
        val viewModelFactory =
            QuizViewModelFactory(requireContext(), requireNotNull(activity).application)
        val viewModel: QuizViewModel =
            ViewModelProvider(this, viewModelFactory).get(QuizViewModel::class.java)
        // Set the viewModel for DataBinding.
        // This allows the bound layout access to all the data in the ViewModel.
        binding.quizViewModel = viewModel

        /*
         * Initiate the quiz.
         */
        // Get answer views.
        answersView = mutableListOf(
            binding.tvAnswerOne,
            binding.tvAnswerTwo,
            binding.tvAnswerThree,
            binding.tvAnswerFour
        )
        // Get selected continent for questions.
        selectedContinent()
        // Submit the answer.
        binding.btnSubmit.setOnClickListener { submitAnswer(it) }

        return binding.root
    }

    /*
     * Get selected continent from HomeFragment with SafeArgs.
     * And set title fragment.
     */
    private fun selectedContinent() {
        val args = QuizFragmentArgs.fromBundle(requireArguments())
        when (args.continent) {
            getString(R.string.continent_europe) -> {
                questions = EUROPE.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_europe)
            }
            getString(R.string.continent_america) -> {
                questions = AMERICA.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_america)
            }
        }
        setQuestion()
        selectedAnswerView()
    }

    /**
     * Setting the question to UI components.
     */
    private fun setQuestion() {
        randomizeQuestions()

        // Randomize the answers into a copy of the mutableList and shuffle them.
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()

        // Reset the view to default.
        defaultAnswerView()

        // Set the progress
        binding.progressBar.progress = indexQuestion.plus(1)
        binding.progressBar.max = numQuestions
        binding.tvProgressBar.text =
            getString(R.string.progress, indexQuestion.plus(1), numQuestions)

        // Set the view to the current question.
        binding.tvQuestion.text = currentQuestion.question
        binding.ivFlag.setImageResource(currentQuestion.image)
        for ((index, answer) in answersView.withIndex()) {
            answer.text = answers[index]
            // Enable to select an option.
            answer.isClickable = true
        }

        // Reset the button to "Submit".
        binding.btnSubmit.text = getString(R.string.btn_submit)

        // Cannot submit question if a option is not selected.
        submitQuestion = false
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        // Get question.
        currentQuestion = questions[indexQuestion]
        // TODO: Remove current question.
    }

    /*
     * Set the view of answersView to default.
     */
    private fun defaultAnswerView() {
        for (answer in answersView) {
            answer.setTextColor((Color.parseColor("#7A8089")))
            answer.typeface = Typeface.DEFAULT
            answer.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.default_option_border_bg
            )
        }
    }

    /**
     * Get the selected index answer and set the view.
     */
    private fun selectedAnswerView() {
        for ((index, answer) in answersView.withIndex()) {
            answer.setOnClickListener {
                defaultAnswerView()

                // Set the option view to selected.
                answer.setTextColor((Color.parseColor("#363A43")))
                answer.setTypeface(answer.typeface, Typeface.BOLD)
                answer.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.selected_option_border_bg
                    )

                // Get the selected option.
                indexAnswer = index
                // Enable to select an option.
                answer.isClickable = true
                // Can pass to another question.
                submitQuestion = true
            }
        }
    }

    /*
     * Highlight the answer for wrong or right.
     */
    private fun highlightAnswerView(answer: String, drawableView: Int) {
        for ((index) in answersView.withIndex()) {
            when (answer) {
                answers[index] -> answersView[index].background =
                    ContextCompat.getDrawable(requireContext(), drawableView)
            }
        }
    }

    /*
     * Submit the answer.
     */
    private fun submitAnswer(view: View) {
        when {
            lives <= 0 -> view.findNavController()
                .navigate(QuizFragmentDirections.actionQuizFragmentToLoseFragment())

            // Show a toast if trying to submit question without an option.
            !submitQuestion -> Toast.makeText(
                requireContext(),
                getString(R.string.please_select_option),
                Toast.LENGTH_SHORT
            ).show()

            // When pass to another question.
            // The number 5 is used because there is no index 5, so can continue the quiz.
            indexAnswer == 5 -> {
                indexQuestion++
                when {
                    indexQuestion < numQuestions -> {
                        setQuestion()
                        selectedAnswerView()
                    }
                    else -> view.findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToWonFragment(
                            correctAnswers,
                            numQuestions
                        )
                    )
                }
            }

            // When option selected check for correct or wrong answer.
            else -> {
                // If option wrong.
                if (answers[indexAnswer] != currentQuestion.answers[0]) {
                    // Mark selected view to wrong.
                    highlightAnswerView(answers[indexAnswer], R.drawable.wrong_option_border_bg)

                    // Decrease lives.
                    lives--
                    when (lives) {
                        2 -> binding.ivLiveOne.visibility = View.GONE
                        1 -> binding.ivLiveTwo.visibility = View.GONE
                        0 -> binding.ivLiveThree.visibility = View.GONE
                    }
                } else {
                    correctAnswers++
                }

                // Always check for correct answer.
                highlightAnswerView(
                    currentQuestion.answers[0],
                    R.drawable.correct_option_border_bg
                )

                // When submit answer cannot change answersView.
                for (answer in answersView) {
                    answer.isClickable = false
                }

                // When the questions are over, button Submit change to finish.
                when {
                    indexQuestion + 1 == numQuestions ->
                        binding.btnSubmit.text = getString(R.string.btn_finish)
                    lives == 0 -> binding.btnSubmit.text = getString(R.string.btn_finish)
                    else -> binding.btnSubmit.text = getString(R.string.btn_next_question)
                }

                // Reset selected option for pass to another question.
                indexAnswer = 5
            }
        }
    }
}