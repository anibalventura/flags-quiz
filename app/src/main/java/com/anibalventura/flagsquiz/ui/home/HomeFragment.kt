package com.anibalventura.flagsquiz.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.SharedPreferences
import com.anibalventura.flagsquiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    // Use DataBinding.
    private lateinit var binding: FragmentHomeBinding

    private lateinit var optionsView: MutableList<TextView>
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
        // Get and set the current context for SharedPreferences.
        val sharedPreferences = SharedPreferences(requireContext())
        // Get and set the current username.
        binding.textView.text = sharedPreferences.getString("user_name", "")

        optionsView = mutableListOf(binding.tvEurope, binding.tvAmerica)

        // Get option selected.
        selectedOptionsView()

        // Start the fragment quiz.
        binding.btnStart.setOnClickListener { startQuiz(it) }

        return binding.root
    }

    /**
     * Set the option view to selected.
     */
    private fun selectedOptionsView() {
        for ((index, answer) in optionsView.withIndex()) {
            answer.setOnClickListener {
                defaultOptionsView()

                // Get the selected option.
                selectedContinent = index + 1

                // Set the option view to selected.
                answer.setTextColor((Color.parseColor("#363A43")))
                answer.setTypeface(answer.typeface, Typeface.BOLD)
                answer.background = ContextCompat.getDrawable(
                    requireContext(), R.drawable.selected_option_border_bg
                )
            }
        }
    }

    /*
     * Set the view of options to default.
     */
    private fun defaultOptionsView() {
        for (option in optionsView) {
            option.setTextColor((Color.parseColor("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.default_option_border_bg
            )
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
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_europe)))
                selectedContinent = 0
            }
            2 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment(getString(R.string.continent_america)))
                selectedContinent = 0
            }
            else -> Toast.makeText(
                requireContext(),
                getString(R.string.please_select_continent),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}