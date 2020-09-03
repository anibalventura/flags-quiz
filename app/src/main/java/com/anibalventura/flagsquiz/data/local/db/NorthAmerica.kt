package com.anibalventura.flagsquiz.data.local.db

import com.anibalventura.flagsquiz.R

/*
 * Contains all the dara for the North America continent.
 */
object NorthAmerica {
    fun getQuestions(): MutableList<Question> {
        return mutableListOf(
            Question(
                R.drawable.na_antigua_and_barbuda_flag,
                listOf("Antigua and Barbuda", "X", "X", "X")
            ),
            Question(
                R.drawable.na_bahamas_flag,
                listOf("Bahamas", "X", "X", "X")
            ),
            Question(
                R.drawable.na_barbados_flag,
                listOf("Barbados", "X", "X", "X")
            ),
            Question(
                R.drawable.na_belize_flag,
                listOf("Belize", "X", "X", "X")
            ),
            Question(
                R.drawable.na_canada_flag,
                listOf("Canada", "X", "X", "X")
            ),
            Question(
                R.drawable.na_costa_rica_flag,
                listOf("Costa Rica", "X", "X", "X")
            ),
            Question(
                R.drawable.na_cuba_flag,
                listOf("Cuba", "X", "X", "X")
            ),
            Question(
                R.drawable.na_dominica_flag,
                listOf("Dominica", "X", "X", "X")
            ),
            Question(
                R.drawable.na_dominican_republic_flag,
                listOf("Dominican Republic", "X", "X", "X")
            ),
            Question(
                R.drawable.na_el_salvador_flag,
                listOf("El Salvador", "X", "X", "X")
            ),
            Question(
                R.drawable.na_grenada_flag,
                listOf("Grenada", "X", "X", "X")
            ),
            Question(
                R.drawable.na_guatemala_flag,
                listOf("Guatemala", "X", "X", "X")
            ),
            Question(
                R.drawable.na_haiti_flag,
                listOf("Haiti", "X", "X", "X")
            ),
            Question(
                R.drawable.na_honduras_flag,
                listOf("Honduras", "X", "X", "X")
            ),
            Question(
                R.drawable.na_jamaica_flag,
                listOf("Jamaica", "X", "X", "X")
            ),
            Question(
                R.drawable.na_mexico_flag,
                listOf("Mexico", "X", "X", "X")
            ),
            Question(
                R.drawable.na_nicaragua_flag,
                listOf("Nicaragua", "X", "X", "X")
            ),
            Question(
                R.drawable.na_saint_kitts_and_nevis_flag,
                listOf("Saint Kitts and Nevis", "X", "X", "X")
            ),
            Question(
                R.drawable.na_united_states_of_america_flag,
                listOf("United States of America", "X", "X", "X")
            )
        )
    }
}