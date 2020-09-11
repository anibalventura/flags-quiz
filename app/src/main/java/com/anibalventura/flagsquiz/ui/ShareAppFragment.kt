package com.anibalventura.flagsquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.databinding.FragmentShareAppBinding

class ShareAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        val binding: FragmentShareAppBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_share_app, container, false)

        // Share text.
        Utils.shareText(requireContext(), getString(R.string.share_app))

        // Go back to home.
        findNavController().navigate(ShareAppFragmentDirections.actionShareAppFragmentToHomeFragment2())

        return binding.root
    }
}