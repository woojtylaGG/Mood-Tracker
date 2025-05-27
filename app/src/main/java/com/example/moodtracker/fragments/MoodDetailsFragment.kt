package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.databinding.FragmentMoodDetailsBinding

class MoodDetailsFragment : Fragment() {

    private var _binding: FragmentMoodDetailsBinding? = null
    private val binding get() = _binding!!

    private var moodEntry: MoodEntry? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moodId = arguments?.getString("moodId")
        moodEntry = FakeMoodRepository.getMoodById(moodId)

        moodEntry?.let { entry ->
            displayMoodDetails(entry)
        }

        binding.deleteButton.setOnClickListener {
            Toast.makeText(context, "Wpis usunięty!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayMoodDetails(entry: MoodEntry) {
        binding.apply {
            moodDescription.text = entry.note
            moodCategory.text = entry.category
            moodActivity.text = if (entry.wasActive) "Aktywny" else "Nieaktywny"
            moodSleep.text = if (entry.sleepWell) "Dobry sen" else "Zły sen"
            moodRating.text = "Ocena dnia: ${entry.rating}"
            moodDate.text = "Data: ${entry.date}"

            val moodColor = when (entry.mood) {
                2 -> android.R.color.holo_green_light
                1 -> android.R.color.darker_gray
                0 -> android.R.color.holo_blue_light
                else -> android.R.color.darker_gray
            }
            moodIndicator.setBackgroundColor(resources.getColor(moodColor, null))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}