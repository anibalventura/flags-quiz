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
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.data.local.db.*
import com.anibalventura.flagsquiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    // Use DataBinding.
    private lateinit var binding: FragmentQuizBinding

    // Flags.
    private lateinit var flags: MutableList<Flags> // Flags from database.
    private lateinit var currentFlag: Flags
    private var indexFlag: Int = 0
    private var submitFlag: Boolean = false
    private val numFlags = 3

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

        // Get selected continent for flags.
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
                flags = Africa.getFlags()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_africa)
            }
            getString(R.string.continent_asia) -> {
                flags = Asia.getFlags()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_asia)
            }
            getString(R.string.continent_europe) -> {
                flags = Europe.getFlags()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_europe)
            }
            getString(R.string.continent_north_america) -> {
                flags = NorthAmerica.getFlags()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_north_america)
            }
            getString(R.string.continent_oceania) -> {
                flags = Oceania.getFlags()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_oceania)
            }
            getString(R.string.continent_south_america) -> {
                flags = SouthAmerica.getFlags()
                (activity as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.continent_south_america)
            }
        }
        // Set flag from selected continent.
        setFlag()
        // Update answer view on user selection.
        selectedAnswerView()
    }

    /**
     * Setting the flag to UI components.
     */
    private fun setFlag() {
        // Set random flag and answer.
        randomFlagAndAnswer()

        // Reset the view to default.
        defaultAnswerView()

        // Update the progress bar.
        binding.progressBar.progress = indexFlag.plus(1)
        binding.progressBar.max = numFlags
        binding.tvProgressBar.text =
            getString(R.string.quiz_progress, indexFlag.plus(1), numFlags)

        // Update the view to the current flag.
        binding.ivFlag.setImageResource(currentFlag.image)
        for ((index, answer) in answersView.withIndex()) {
            answer.text = answers[index]
            // Enable to select an answer.
            answer.isClickable = true
        }

        // Update button view to "Submit".
        binding.btnSubmit.text = getString(R.string.quiz_btn_submit)

        // Cannot submit flag if a answer is not selected.
        submitFlag = false
    }

    /*
     * Random flag and answers orders.
     */
    private fun randomFlagAndAnswer() {
        // Set random order for flags.
        flags.shuffle()
        // Update current flag.
        currentFlag = flags[indexFlag]
        // Set answers into a copy of the mutableList.
        answers = currentFlag.answers.toMutableList()
        // Set random order for answers.
        answers.shuffle()
    }

    /*
     * Update answers view.
     */
    // Set the view of answersView to default.
    private fun defaultAnswerView() {
        for (answer in answersView) {
            answer.typeface = Typeface.DEFAULT
            answer.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.bg_default_answer_border
            )
        }
    }

    // Get the selected index answer and set the view.
    private fun selectedAnswerView() {
        for ((index, answer) in answersView.withIndex()) {
            answer.setOnClickListener {
                defaultAnswerView()

                // Set the answer view to selected.
                answer.setTypeface(answer.typeface, Typeface.BOLD)
                answer.background =
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bg_selected_answer_border
                    )

                // Get the selected answer.
                indexAnswer = index
                // Enable to select an answer.
                answer.isClickable = true
                // Can pass to another flag.
                submitFlag = true
            }
        }
    }

    // Highlight the answer selected for wrong or right.
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

            // Show a toast if trying to submit flag without an answer.
            !submitFlag -> Utils.showToast(
                requireContext(),
                getString(R.string.quiz_select_answer)
            )

            // When pass to another flag.
            indexAnswer == 5 -> { // The number 5 have no value, is only used to continue the quiz.
                // Update index to pass to another flag.
                indexFlag++

                when {
                    // If number of flags answered not meet the total flags, continue the quiz.
                    indexFlag < numFlags -> {
                        // Set a new flag.
                        setFlag()
                        // Update selected answer view.
                        selectedAnswerView()
                    }
                    // Go to QuizWonFragment when finish the quiz and pass arguments.
                    else -> view.findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToWonFragment(
                            correctAnswers,
                            numFlags,
                            args.continent
                        )
                    )
                }
            }

            // When answer selected check for correct or wrong answer.
            else -> {
                // If answer wrong.
                if (answers[indexAnswer] != currentFlag.answers[0]) {
                    // Mark selected view to wrong.
                    highlightAnswerView(answers[indexAnswer], R.drawable.bg_wrong_answer_border)

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
                highlightAnswerView(currentFlag.answers[0], R.drawable.bg_correct_answer_border)

                // When answer is submit, user cannot change the answer.
                for (answer in answersView) {
                    answer.isClickable = false
                }

                when {
                    // When the flags are over, button Submit change to finish.
                    indexFlag + 1 == numFlags ->
                        binding.btnSubmit.text = getString(R.string.quiz_btn_finish)
                    // When the user lose, button submit change to finish.
                    lives == 0 -> binding.btnSubmit.text = getString(R.string.quiz_btn_finish)
                    // Or update button to next flag.
                    else -> binding.btnSubmit.text = getString(R.string.quiz_btn_next_flag)
                }

                // Reset selected answer for pass to another flag.
                indexAnswer = 5
            }
        }
    }
}