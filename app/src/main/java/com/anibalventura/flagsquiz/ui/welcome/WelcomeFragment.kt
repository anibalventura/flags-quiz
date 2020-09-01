package com.anibalventura.flagsquiz.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var name: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        /**
         * Specify the fragment view as the lifecycle owner of the binding.
         * This is used so that the binding can observe LiveData updates
         */
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnStart.setOnClickListener { view: View ->
            // Show a Toast message when don't have a user name
            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_enter_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val userName: EditText = binding.etName
                val name = userName.text.toString()
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment(name)
                view.findNavController().navigate(action)
            }
        }
        return binding.root
    }
}