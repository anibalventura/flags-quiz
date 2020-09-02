package com.anibalventura.flagsquiz.ui.won

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.SharedPreferences
import com.anibalventura.flagsquiz.databinding.FragmentWonBinding

class WonFragment : Fragment() {

    // Use DataBinding.
    private lateinit var binding: FragmentWonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_won, container, false
        )
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        /*
         * SharedPreferences.
         */
        // Get and set the current context for SharedPreferences.
        val sharedPreferences = SharedPreferences(requireContext())
        // Get and set the current username.
        binding.tvResultUsername.text = sharedPreferences.getString("user_name", "")

        /*
         * SafeArgs.
         */
        // Get the arguments from the WonFragment, passed from QuizFragment.
        val args = WonFragmentArgs.fromBundle(requireArguments())
        // Set the score view with the arguments.
        binding.tvResultScore.text =
            getString(R.string.total_score, args.correntAnswers, args.totalQuestions)

        // Back to the home fragment.
        binding.btnResultFinish.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToHomeFragment())
        }

        return binding.root
    }
}