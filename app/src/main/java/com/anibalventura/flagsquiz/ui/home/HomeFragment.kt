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

    private var selectedContinent: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        /**
         * Specify the fragment view as the lifecycle owner of the binding.
         * This is used so that the binding can observe LiveData updates
         */
        binding.lifecycleOwner = viewLifecycleOwner

        // Get and set the current context for SharedPreferences.
        val sharedPreferences = SharedPreferences(requireContext())

        // Get and ser the current username.
        val userName = sharedPreferences.getString("user_name", "")
        binding.textView.text = userName

        // Get option selected.
        selectedOptionsView(binding.tvEurope, 1)
        selectedOptionsView(binding.tvAmerica, 2)

        // Start the fragment quiz.
        binding.btnStart.setOnClickListener { view: View ->
            startQuiz(view)
        }

        return binding.root
    }

    /*
     * Submit the answer.
     */
    private fun startQuiz(view: View) {
        when (selectedContinent) {
            1 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment("EUROPE"))
                selectedContinent = 0
            }
            2 -> {
                view.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment("AMERICA"))
                selectedContinent = 0
            }
            else -> Toast.makeText(
                requireContext(),
                getString(R.string.please_select_continent),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Set the option view to selected.
     */
    private fun selectedOptionsView(tv: TextView, selectionContinent: Int) {
        tv.setOnClickListener {

            defaultOptionsView()

            // Get the selected option.
            selectedContinent = selectionContinent

            // Set the option view to selected.
            tv.setTextColor((Color.parseColor("#363A43")))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.selected_option_border_bg
            )
        }
    }

    /*
     * Set the view of options to default.
     */
    private fun defaultOptionsView() {
        val options: MutableList<TextView> = mutableListOf(binding.tvEurope, binding.tvAmerica)
        for (option in options) {
            option.setTextColor((Color.parseColor("#7A8089")))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                requireContext(), R.drawable.default_option_border_bg
            )
        }
    }
}