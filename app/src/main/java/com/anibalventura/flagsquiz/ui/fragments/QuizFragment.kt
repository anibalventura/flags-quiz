package com.anibalventura.flagsquiz.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils.Companion.showToast
import com.anibalventura.flagsquiz.data.local.db.*
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
            getString(R.string.continent_africa) -> {
                questions = Africa.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_africa)
            }
            getString(R.string.continent_asia) -> {
                questions = Asia.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_asia)
            }
            getString(R.string.continent_europe) -> {
                questions = Europe.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_europe)
            }
            getString(R.string.continent_north_america) -> {
                questions = NorthAmerica.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_north_america)
            }
            getString(R.string.continent_oceania) -> {
                questions = Oceania.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_oceania)
            }
            getString(R.string.continent_south_america) -> {
                questions = SouthAmerica.getQuestions()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_south_america)
            }
        }
        // Set question from selected continent.
        setQuestion()
        // Update answer view on user selection.
        selectedAnswerView()
    }

    /**
     * Setting the question to UI components.
     */
    private fun setQuestion() {
        // Get a random question.
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
            getString(R.string.quiz_progress, indexQuestion.plus(1), numQuestions)

        // Set the view to the current question.
        binding.ivFlag.setImageResource(currentQuestion.image)
        for ((index, answer) in answersView.withIndex()) {
            answer.text = answers[index]
            // Enable to select an option.
            answer.isClickable = true
        }

        // Reset the button to "Submit".
        binding.btnSubmit.text = getString(R.string.quiz_btn_submit)

        // Cannot submit question if a option is not selected.
        submitQuestion = false
    }

    /*
     * Get a random question.
     */
    private fun randomizeQuestions() {
        // Set random order for questions.
        questions.shuffle()
        // Get question.
        currentQuestion = questions[indexQuestion]
    }

    /*
     * Set the view of answersView to default.
     */
    private fun defaultAnswerView() {
        for (answer in answersView) {
            answer.typeface = Typeface.DEFAULT
            answer.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.bg_default_option_border
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
                answer.setTypeface(answer.typeface, Typeface.BOLD)
                answer.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bg_selected_option_border
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
     * Highlight the answer selected for wrong or right.
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
        val args = QuizFragmentArgs.fromBundle(requireArguments())

        when {
            // When out of live, go to QuizOverFragment.
            lives <= 0 -> view.findNavController()
                .navigate(QuizFragmentDirections.actionQuizFragmentToLoseFragment(args.continent))

            // Show a toast if trying to submit question without an option.
            !submitQuestion -> showToast(requireContext(), getString(R.string.quiz_select_option))

            // When pass to another question.
            indexAnswer == 5 -> { // The number 5 have no value, is only used to continue the quiz.
                // Update index to pass to another question.
                indexQuestion++

                when {
                    // If number of questions answered not meet the total questions, continue the quiz.
                    indexQuestion < numQuestions -> {
                        // Set a new question.
                        setQuestion()
                        // Update selected answer view.
                        selectedAnswerView()
                    }
                    // Go to QuizWonFragment when finish the quiz and pass arguments.
                    else -> view.findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToWonFragment(
                            correctAnswers,
                            numQuestions,
                            args.continent
                        )
                    )
                }
            }

            // When option selected check for correct or wrong answer.
            else -> {
                // If option wrong.
                if (answers[indexAnswer] != currentQuestion.answers[0]) {
                    // Mark selected view to wrong.
                    highlightAnswerView(answers[indexAnswer], R.drawable.bg_wrong_option_border)

                    // Decrease lives.
                    lives--
                    // Hide livesView when wrong answer.
                    when (lives) {
                        2 -> binding.ivLiveOne.visibility = View.GONE
                        1 -> binding.ivLiveTwo.visibility = View.GONE
                        0 -> binding.ivLiveThree.visibility = View.GONE
                    }
                } else {
                    correctAnswers++
                }

                // Always check for correct answer.
                highlightAnswerView(currentQuestion.answers[0], R.drawable.bg_correct_option_border)

                // When answer is submit, user cannot change the answer.
                for (answer in answersView) {
                    answer.isClickable = false
                }

                when {
                    // When the questions are over, button Submit change to finish.
                    indexQuestion + 1 == numQuestions ->
                        binding.btnSubmit.text = getString(R.string.quiz_btn_finish)
                    // When the user lose, button submit change to finish.
                    lives == 0 -> binding.btnSubmit.text = getString(R.string.quiz_btn_finish)
                    // Or update button to next flag.
                    else -> binding.btnSubmit.text = getString(R.string.quiz_btn_next_flag)
                }

                // Reset selected option for pass to another question.
                indexAnswer = 5
            }
        }
    }
}