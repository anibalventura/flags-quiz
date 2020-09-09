package com.anibalventura.flagsquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        val binding: FragmentAboutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}
