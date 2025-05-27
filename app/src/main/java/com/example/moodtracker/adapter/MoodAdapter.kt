package com.example.moodtracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.databinding.ItemMoodBinding

class MoodAdapter(
    private var moodList: List<MoodEntry>,
    private val onItemClick: (MoodEntry) -> Unit
) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMoodBinding.bind(view)

        fun bind(moodEntry: MoodEntry) {
            binding.dateTextView.text = moodEntry.date
            binding.noteTextView.text = moodEntry.note
            itemView.setOnClickListener { onItemClick(moodEntry) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        holder.bind(moodList[position])
    }

    override fun getItemCount(): Int = moodList.size

    fun updateData(newMoodList: List<MoodEntry>) {
        moodList = newMoodList
        notifyDataSetChanged()
    }
}