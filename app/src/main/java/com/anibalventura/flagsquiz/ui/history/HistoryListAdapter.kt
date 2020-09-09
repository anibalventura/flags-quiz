package com.anibalventura.flagsquiz.ui.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anibalventura.flagsquiz.R
import com.anibalventura.flagsquiz.Utils
import com.anibalventura.flagsquiz.data.local.db.History

class HistoryListAdapter internal constructor() :
    RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder>() {

    val utils = Utils.resourses!!

    private var history: List<History> = emptyList() // Cached copy of words

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultItemView: TextView = itemView.findViewById(R.id.tvHistoryResult)
        val scoreItemView: TextView = itemView.findViewById(R.id.tvHistoryScore)
        val continentItemView: TextView = itemView.findViewById(R.id.tvHistoryContinent)
    }

    internal fun setHistory(history: List<History>) {
        this.history = history
        notifyDataSetChanged()
    }

    /**
     * Default override these 3 methods.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_history, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = history[position]

        when (current.result) {
            utils.getString(R.string.quiz_you_won) -> holder.resultItemView.setTextColor(
                Color.parseColor("#B32DAB32")
            )
            utils.getString(R.string.quiz_you_lose) -> holder.resultItemView.setTextColor(
                Color.parseColor("#B3FF0000")
            )
        }

        holder.resultItemView.text = current.result

        holder.scoreItemView.text = utils.getString(
            R.string.history_score,
            current.correctAnswers,
            current.totalAnswers
        )

        holder.continentItemView.text =
            utils.getString(R.string.history_continent, current.continent)
    }
}