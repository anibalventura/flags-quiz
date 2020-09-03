package com.anibalventura.flagsquiz.data.local.db

import com.anibalventura.flagsquiz.R

/*
 * Contains all the dara for the South America continent.
 */
object SouthAmerica {
    fun getQuestions(): MutableList<Question> {
        return mutableListOf(
            Question(
                R.drawable.sa_argentina_flag,
                listOf("Argentina", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_bolivia_flag,
                listOf("Bolivia", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_brazil_flag,
                listOf("Brazil", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_chile_flag,
                listOf("Chile", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_colombia_flag,
                listOf("Colombia", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_ecuador_flag,
                listOf("Ecuador", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_guyana_flag,
                listOf("Guyana", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_panama_flag,
                listOf("Panama", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_paraguay_flag,
                listOf("Paraguay", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_peru_flag,
                listOf("Peru", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_saint_lucia_flag,
                listOf("Saint Lucia", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_saint_vincent_and_the_grenadines_flag,
                listOf("Saint Vincent and the Grenadines", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_suriname_flag,
                listOf("Suriname", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_trinidad_and_tobago_flag,
                listOf("Trinidad and Tobago", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_uruguay_flag,
                listOf("Uruguay", "X", "X", "X")
            ),
            Question(
                R.drawable.sa_venezuela_flag,
                listOf("Venezuela", "X", "X", "X")
            )
        )
    }
}