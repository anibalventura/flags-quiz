package com.anibalventura.flagsquiz.ui.over

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.databinding.FragmentOverBinding

class OverFragment : Fragment() {

    private lateinit var binding: FragmentOverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_over, container, false
        )

        return binding.root
    }
}