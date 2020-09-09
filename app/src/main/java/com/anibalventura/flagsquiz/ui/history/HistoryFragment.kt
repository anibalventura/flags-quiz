package com.anibalventura.flagsquiz.ui.history

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Inflate the layout for this fragment.
         */
        // Use DataBindingUtil.inflate to inflate and return the Fragment in onCreateView.
        val binding: FragmentHistoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        /**
         * Set the RecyclerView adapter.
         */
        val recyclerView = binding.recyclerview
        val adapter = HistoryListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        /**
         * Observe for a change on the calculations list for the adapter.
         */
        val viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        viewModel.allHistory.observe(requireActivity(), { history ->
            // Update the cached copy of the words in the adapter.
            history?.let { adapter.setHistory(it) }
        })

        return binding.root
    }

    /**
     * Set options menu.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.deleteHistoryMenu -> {
                val fm = childFragmentManager
                DeleteDialogFragment()
                    .show(fm, "Delete History Dialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}