package com.anibalventura.flagsquiz.ui.won

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.CONSTANTS
import com.anibalventura.flagsquiz.databinding.FragmentWonBinding
import com.anibalventura.flagsquiz.ui.quiz.QuizFragment
import com.anibalventura.flagsquiz.ui.quiz.QuizViewModel
import com.anibalventura.flagsquiz.ui.quiz.QuizViewModelFactory

class WonFragment : Fragment() {

    private lateinit var binding: FragmentWonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_won, container, false)
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


        val intent = Intent(requireContext(), QuizFragment::class.java)
        val username = intent.getStringExtra(CONSTANTS.USER_NAME)
        binding.tvResultUsername.text = username

        val correctAnswers = intent.getIntExtra(CONSTANTS.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(CONSTANTS.TOTAL_QUESTIONS, 0)
        binding.tvResultScore.text = getString(R.string.total_score, correctAnswers, totalQuestions)

        binding.btnResultFinish.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment())
        }

        return binding.root
    }
}