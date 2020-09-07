package com.anibalventura.flagsquiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.data.local.CONST
import com.anibalventura.flagsquiz.databinding.FragmentQuizOverBinding

class QuizOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        val binding: FragmentQuizOverBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_over, container, false)
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        /*
         * SharedPreferences.
         */
        // // Get name from SharedPreferences.
        val username = Utils.sharedPref(requireContext()).getString(CONST.USER_NAME, "")
        // Update name view from SharedPreferences.
        binding.tvResultSorry.text = getString(R.string.quiz_sorry_lose, username)

        /*
         * Buttons.
         */
        // Back to the HomeFragment.
        binding.btnWonFinish.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToHomeFragment())
        }
        // Back to the QuizFragment
        binding.btnTryAgain.setOnClickListener { startQuiz(it) }

        return binding.root
    }

    /*
     * Start quiz on selected continent.
     * Pass the selected continent via SafeArgs to QuizFragment.
     */
    private fun startQuiz(view: View) {
        val args = QuizWonFragmentArgs.fromBundle(requireArguments())

        when (args.selectedContinent) {
            getString(R.string.continent_africa) -> view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToQuizFragment(getString(R.string.continent_africa)))
            getString(R.string.continent_asia) -> view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToQuizFragment(getString(R.string.continent_asia)))
            getString(R.string.continent_europe) -> view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToQuizFragment(getString(R.string.continent_europe)))
            getString(R.string.continent_north_america) -> view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToQuizFragment(getString(R.string.continent_north_america)))
            getString(R.string.continent_oceania) -> view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToQuizFragment(getString(R.string.continent_oceania)))
            getString(R.string.continent_south_america) -> view.findNavController()
                .navigate(QuizOverFragmentDirections.actionLoseFragmentToQuizFragment(getString(R.string.continent_south_america)))
        }
    }
}