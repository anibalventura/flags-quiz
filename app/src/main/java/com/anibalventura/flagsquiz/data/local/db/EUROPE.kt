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
                listOf("Argentina", "Australia", "Armenia", "Austria")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_australia,
                listOf("Australia", "Angola", "Austria", "Armenia")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_brazil,
                listOf("Brazil", "Belarus", "Belize", "Brunei")
            ), Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_belgium,
                listOf("Belgium", "Bahamas", "Barbados", "Belize")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_fiji,
                listOf("Fiji", "Gabon", "France", "Finland")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_germany,
                listOf("Germany", "Georgia", "Greece", "Italy")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_denmark,
                listOf("Denmark", "Dominica", "Egypt", "Ethiopia")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_india,
                listOf("India", "Ireland", "Iran", "Hungary")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_new_zealand,
                listOf("New Zealand", "Australia", "Tuvalu", "United States of America")
            ),
            Question(
                App.resourses!!.getString(R.string.what_flag_question),
                R.drawable.ic_flag_of_kuwait,
                listOf("Kuwait", "Jordan", "Sudan", "Palestine")
            )
        )
    }
}