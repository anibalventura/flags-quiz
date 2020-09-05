package com.anibalventura.flagsquiz.ui.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.data.local.sharedpref.CONST
import com.anibalventura.flagsquiz.data.local.sharedpref.SharedPreferences

class ThemeDialogFragment : DialogFragment() {

    // Initialize SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            // Get the current context for SharedPreferences
            sharedPreferences =
                SharedPreferences(
                    requireContext()
                )

            // Theme options for the dialog
            val themes = arrayOf(
                getString(R.string.light_theme), getString(R.string.dark_theme), getString(
                    R.string.system_default_theme
                )
            )
            val checkedTheme = sharedPreferences.getInt(CONST.CHECKED_THEME, 2)

            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.theme_dialog_title))
                .setSingleChoiceItems(themes, checkedTheme) { _, which ->
                    when (themes[which]) {
                        getString(R.string.light_theme) -> setTheme(
                            AppCompatDelegate.MODE_NIGHT_NO,
                            1,
                            0
                        )
                        getString(R.string.dark_theme) -> setTheme(
                            AppCompatDelegate.MODE_NIGHT_YES,
                            2,
                            1
                        )
                        getString(
                            R.string.system_default_theme
                        ) -> setTheme(
                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                            0,
                            2
                        )
                    }
                }
                .setNegativeButton(getString(R.string.cancel_dialog_option)) { dialog, _ -> dialog.dismiss() }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    /**
     * Configure theme and set SharedPreferences values
     */
    private fun setTheme(mode: Int, theme: Int, checkedTheme: Int) {
        sharedPreferences.putInt(CONST.THEME, theme)
        sharedPreferences.putInt(CONST.CHECKED_THEME, checkedTheme)
        dialog?.dismiss()
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}

//class AboutDialogFragment : DialogFragment() {
//
//    private var dialogView: View? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.about_dialog, container, false)
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return MaterialAlertDialogBuilder(requireContext(), theme).apply {
//            dialogView =
//                onCreateView(LayoutInflater.from(requireContext()), null, savedInstanceState)
//            dialogView?.let { onViewCreated(it, savedInstanceState) }
//            setView(dialogView)
//        }.create()
//    }
//
//    override fun getView(): View? {
//        return dialogView
//    }
//}