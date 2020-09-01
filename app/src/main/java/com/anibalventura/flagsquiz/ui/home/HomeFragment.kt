package com.anibalventura.flagsquiz.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.SharedPreferences
import com.anibalventura.flagsquiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    // Initialize SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

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

        // Get the current context for SharedPreferences
        sharedPreferences = SharedPreferences(requireContext())

        binding.textView.text = sharedPreferences.getString("user_name", "")

        binding.btnStart.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToQuizFragment())
        }

        return binding.root
    }
}