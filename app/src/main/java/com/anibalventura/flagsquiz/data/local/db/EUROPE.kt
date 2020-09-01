package com.anibalventura.flagsquiz.data.local.db

import com.anibalventura.flagsquiz.App
import com.anibalventura.flagsquiz.R

/*
 * Contains all the question of the Europe continent.
 */
object EUROPE {

    fun getQuestions(): MutableList<Question> {
        return mutableListOf(
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_argentina,
                "Argentina", "Australia",
                "Armenia", "Austria", 1
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_australia,
                "Angola", "Austria",
                "Australia", "Armenia", 3
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_brazil,
                "Belarus", "Belize",
                "Brunei", "Brazil", 4
            ), Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_belgium,
                "Bahamas", "Belgium",
                "Barbados", "Belize", 2
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_fiji,
                "Gabon", "France",
                "Fiji", "Finland", 3
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_germany,
                "Germany", "Georgia",
                "Greece", "none of these", 1
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_denmark,
                "Dominica", "Egypt",
                "Denmark", "Ethiopia", 3
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_india,
                "Ireland", "Iran",
                "Hungary", "India", 4
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_new_zealand,
                "Australia", "New Zealand",
                "Tuvalu", "United States of America", 2
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_kuwait,
                "Kuwait", "Jordan",
                "Sudan", "Palestine", 1
            )
        )
    }
}