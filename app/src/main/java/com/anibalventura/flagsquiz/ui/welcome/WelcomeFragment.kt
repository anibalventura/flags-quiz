package com.anibalventura.flagsquiz.ui.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.CONSTANTS
import com.anibalventura.flagsquiz.databinding.FragmentWelcomeBinding
import com.anibalventura.flagsquiz.ui.quiz.QuizFragment
import com.anibalventura.flagsquiz.ui.quiz.QuizViewModel
import com.anibalventura.flagsquiz.ui.quiz.QuizViewModelFactory
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment
         */
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
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

        binding.btnStart.setOnClickListener { view: View ->
            // Show a Toast message when don't have a user name
            if (etName.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_enter_name),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                val intent = Intent(requireContext(), QuizFragment::class.java)
                intent.putExtra(CONSTANTS.USER_NAME, binding.etName.text.toString())
                view.findNavController()
                    .navigate(WelcomeFragmentDirections.actionWelcomeFragmentToQuizFragment())
            }
        }

        return binding.root
    }
}