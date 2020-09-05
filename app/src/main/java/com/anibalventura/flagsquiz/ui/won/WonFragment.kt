package com.anibalventura.flagsquiz.ui.won

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.sharedpref.CONST
import com.anibalventura.flagsquiz.data.local.sharedpref.SharedPreferences
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
        val sharedPref = SharedPreferences(requireContext())
        // Get and set the current username.
        val username = sharedPref.getString(CONST.USER_NAME, "")
        binding.tvResultCongrats.text = getString(R.string.won_congrats, username)

        /*
         * SafeArgs.
         */
        // Get the arguments from the WonFragment, passed from QuizFragment.
        val args = WonFragmentArgs.fromBundle(requireArguments())
        // Set the score view with the arguments.
        binding.tvResultScore.text =
            getString(R.string.won_total_score, args.correntAnswers, args.totalQuestions)

        // Back to the HomeFragment.
        binding.btnWonFinish.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToHomeFragment())
        }

        // Back to the QuizFragment
        binding.btnPlayAgain.setOnClickListener { startQuiz(it) }

        return binding.root
    }

    /*
     * Start quiz on selected continent.
     * Pass the selected continent via SafeArgs to QuizFragment.
     */
    private fun startQuiz(view: View) {
        val args = WonFragmentArgs.fromBundle(requireArguments())
        when (args.selectedContinent) {
            getString(R.string.continent_africa) -> view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_africa)))
            getString(R.string.continent_asia) -> view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_asia)))
            getString(R.string.continent_europe) -> view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_europe)))
            getString(R.string.continent_north_america) -> view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_north_america)))
            getString(R.string.continent_oceania) -> view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_oceania)))
            getString(R.string.continent_south_america) -> view.findNavController()
                .navigate(WonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_south_america)))
        }
    }
}