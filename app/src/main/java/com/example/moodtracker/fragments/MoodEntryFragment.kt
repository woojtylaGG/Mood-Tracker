package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.databinding.FragmentMoodEntryBinding

class MoodEntryFragment : Fragment() {

    private var _binding: FragmentMoodEntryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.mood_entry_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_history -> {
                    findNavController().navigate(R.id.action_moodEntry_to_moodHistory)
                    true
                }
                R.id.action_settings -> {
                    findNavController().navigate(R.id.action_moodEntry_to_settingsFragment)
                    true
                }
                else -> false
            }
        }
        setupSpinner()
        setupSaveButton()
    }

    private fun setupSpinner() {
        val categories = arrayOf("Szkoła", "Dom", "Znajomi", "Zdrowie", "Inne")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            if (validateForm()) {
                saveMoodEntry()
                Toast.makeText(context, "Zapisano wpis!", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
                clearForm()
            }
        }
    }

    private fun validateForm(): Boolean {
        if (binding.noteEditText.text.isEmpty()) {
            binding.noteEditText.error = "Wprowadź notatkę"
            return false
        }
        if (binding.moodRadioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(context, "Wybierz nastrój", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveMoodEntry() {
        val mood = when (binding.moodRadioGroup.checkedRadioButtonId) {
            R.id.happyRadio -> 2
            R.id.neutralRadio -> 1
            R.id.sadRadio -> 0
            else -> 1
        }

        val entry = MoodEntry(
            mood = mood,
            note = binding.noteEditText.text.toString(),
            category = binding.categorySpinner.selectedItem.toString(),
            sleepWell = binding.sleepCheckBox.isChecked,
            wasActive = binding.activeCheckBox.isChecked,
            rating = binding.dayRatingBar.rating,
            isImportant = binding.importantSwitch.isChecked
        )

        FakeMoodRepository.addMood(entry)
    }

    private fun clearForm() {
        binding.apply {
            noteEditText.text.clear()
            moodRadioGroup.clearCheck()
            categorySpinner.setSelection(0)
            sleepCheckBox.isChecked = false
            activeCheckBox.isChecked = false
            dayRatingBar.rating = 0f
            importantSwitch.isChecked = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}