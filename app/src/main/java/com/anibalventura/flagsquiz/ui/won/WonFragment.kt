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

    private lateinit var binding: FragmentWonBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_won, container, false
        )

        /**
         * Specify the fragment view as the lifecycle owner of the binding.
         * This is used so that the binding can observe LiveData updates
         */
        binding.lifecycleOwner = viewLifecycleOwner

        // Set and get the current context for SharedPreferences.
        val sharedPreferences: SharedPreferences = SharedPreferences(requireContext())

        // Get and ser the current username.
        val userName = sharedPreferences.getString("user_name", "")
        binding.tvResultUsername.text = userName

        // Get and set the score of the quiz.
        val args = WonFragmentArgs.fromBundle(requireArguments())
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