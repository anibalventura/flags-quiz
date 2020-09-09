package com.anibalventura.flagsquiz.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anibalventura.flagsquiz.CONST
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.databinding.FragmentQuizWonBinding

class QuizWonFragment : Fragment() {

    // Use DataBinding.
    private lateinit var binding: FragmentQuizWonBinding

    // SafeArgs, get the arguments from the WonFragment, passed from QuizFragment.
    private lateinit var args: QuizWonFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_quiz_won, container, false
        )
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        /*
         * Update name from SharedPreferences.
         */
        // Get the current name.
        val username = Utils.sharedPref(requireContext()).getString(CONST.USER_NAME, "")
        // Update view with name.
        binding.tvResultCongrats.text = getString(R.string.quiz_congrats, username)

        /*
         * SafeArgs.
         */
        // Set the score view with the arguments.
        args = QuizWonFragmentArgs.fromBundle(requireArguments())
        binding.tvResultScore.text =
            getString(R.string.quiz_score, args.correntAnswers, args.totalQuestions)

        /*
         * Buttons.
         */
        // Back to the HomeFragment.
        binding.btnWonFinish.setOnClickListener {
            findNavController().navigate(QuizWonFragmentDirections.actionWonFragmentToHomeFragment())
        }

        // Back to the QuizFragment
        binding.btnPlayAgain.setOnClickListener { startQuiz() }

        return binding.root
    }

    /*
     * Start quiz on selected continent.
     * Pass the selected continent via SafeArgs to QuizFragment.
     */
    private fun startQuiz() {
        when (args.selectedContinent) {
            getString(R.string.continent_africa) -> findNavController().navigate(
                QuizWonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_africa))
            )
            getString(R.string.continent_asia) -> findNavController()
                .navigate(QuizWonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_asia)))
            getString(R.string.continent_europe) -> findNavController()
                .navigate(QuizWonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_europe)))
            getString(R.string.continent_north_america) -> findNavController()
                .navigate(QuizWonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_north_america)))
            getString(R.string.continent_oceania) -> findNavController()
                .navigate(QuizWonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_oceania)))
            getString(R.string.continent_south_america) -> findNavController()
                .navigate(QuizWonFragmentDirections.actionWonFragmentToQuizFragment(getString(R.string.continent_south_america)))
        }
    }

    /*
     * Sharing option.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Showing the Share Menu.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }

    // Sharing from the Menu.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.shareScore -> {
                Utils.shareText(
                    requireContext(),
                    getString(R.string.share_score, args.correntAnswers, args.totalQuestions)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
