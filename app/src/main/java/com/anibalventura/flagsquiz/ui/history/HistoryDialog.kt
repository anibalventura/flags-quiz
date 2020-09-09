package com.anibalventura.flagsquiz.ui.history

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.anibalventura.flagsquiz.R

class DeleteDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            // Set the HistoryViewModel.
            val historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.delete_dialog_title))
                .setMessage(getString(R.string.delete_dialog_message))
                .setPositiveButton(getString(R.string.delete_dialog_confirmation)) { dialog, _ ->
                    historyViewModel.deleteAll()
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.delete_dialog_negative)) { dialog, _ -> dialog.dismiss() }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}