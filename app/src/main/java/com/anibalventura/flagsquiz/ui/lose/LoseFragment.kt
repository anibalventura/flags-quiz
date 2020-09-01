package com.anibalventura.flagsquiz.ui.lose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.databinding.FragmentHomeBinding

class LoseFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lose, container, false
        )

        return binding.root
    }
}