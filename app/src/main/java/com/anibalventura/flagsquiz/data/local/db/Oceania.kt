package com.anibalventura.flagsquiz.data.local.db

import com.anibalventura.flagsquiz.R

/*
 * Contains all the dara for the Oceania continent.
 */
object Oceania {
    fun getQuestions(): MutableList<Question> {
        return mutableListOf(
            Question(
                R.drawable.oc_australia_flag,
                listOf("Australia", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_east_timor_flag,
                listOf("East Timor", "A", "X", "X")
            ),
            Question(
                R.drawable.oc_fiji_flag,
                listOf("Fiji", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_kiribati_flag,
                listOf("Kiribati", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_marshall_islands_flag,
                listOf("Marshall Islands", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_micronesia_flag,
                listOf("Micronesia", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_nauru_flag,
                listOf("Nauru", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_new_zealand_flag,
                listOf("New Zealand", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_niue_flag,
                listOf("Niue", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_palau_flag,
                listOf("Palau", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_papua_new_guinea_flag,
                listOf("Papua New Guinea", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_samoa_flag,
                listOf("Samoa", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_solomon_islands_flag,
                listOf("Solomon Islands", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_tonga_flag,
                listOf("Tonga", "X", "X", "X")
            ),
            Question(
                R.drawable.oc_tuvalu_flag,
                listOf("Tuvalu", "X", "X", "X")
            )
        )
    }
}