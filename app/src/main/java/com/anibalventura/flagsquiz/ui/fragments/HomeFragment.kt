package com.anibalventura.flagsquiz.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.Utils.Companion.showToast
import com.anibalventura.flagsquiz.data.local.CONST
import com.anibalventura.flagsquiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Use DataBinding.
    private lateinit var binding: FragmentHomeBinding

    // Selected option.
    private lateinit var optionsView: MutableList<CardView>
    private var selectedContinent: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        /*
         * SharedPreferences.
         */
        // Get and set the current username.
        val name = Utils.sharedPref(requireContext()).getString(CONST.USER_NAME, "")
        binding.tvWelcome.text = getString(R.string.home_welcome, name)

        optionsView = mutableListOf(
            binding.cvAfrica,
            binding.cvAsia,
            binding.cvEurope,
            binding.cvNorthAmerica,
            binding.cvOceania,
            binding.cvSouthAmerica
        )

        // Get option selected.
        selectedOptionsView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Get and set the current username.
        val name = Utils.sharedPref(requireContext()).getString(CONST.USER_NAME, "")
        binding.tvWelcome.text = getString(R.string.home_welcome, name)
    }

    /**
     * Set the option view to selected.
     */
    private fun selectedOptionsView() {
        for ((index, option) in optionsView.withIndex()) {
            option.setOnClickListener {

                // Get the selected option.
                selectedContinent = index + 1

                startQuiz(it)
            }
        }
    }

    /*
     * Start quiz on selected continent.
     * Pass the selected continent via SafeArgs to QuizFragment.
     */
    private fun startQuiz(view: View) {
        when (selectedContinent) {
            1 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_africa)))
                selectedContinent = 0
            }
            2 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_asia)))
                selectedContinent = 0
            }
            3 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_europe)))
                selectedContinent = 0
            }
            4 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_north_america)))
                selectedContinent = 0
            }
            5 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_oceania)))
                selectedContinent = 0
            }
            6 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_south_america)))
                selectedContinent = 0
            }
            else -> showToast(requireContext(), getString(R.string.home_select_continent))
        }
    }
}