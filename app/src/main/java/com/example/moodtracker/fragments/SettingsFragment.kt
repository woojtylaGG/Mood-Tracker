package com.example.moodtracker.fragments

import android.R
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.moodtracker.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("AppSettings", 0)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDarkModeSwitch()
        setupDefaultMoodSpinner()
        setupUserSignatureEditText()
    }

    private fun setupDarkModeSwitch() {
        val isDarkMode = sharedPreferences.getBoolean("darkMode", false)
        binding.darkModeSwitch.isChecked = isDarkMode

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean("darkMode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun setupDefaultMoodSpinner() {
        val moods = arrayOf("Wesoły", "Przeciętny", "Smutny")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, moods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.defaultMoodSpinner.adapter = adapter

        val defaultMood = sharedPreferences.getString("defaultMood", "Przeciętny") ?: "Przeciętny"
        val position = moods.indexOf(defaultMood)
        binding.defaultMoodSpinner.setSelection(position)

        binding.defaultMoodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sharedPreferences.edit().putString("defaultMood", moods[position]).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nie wykonuj żadnej akcji
            }
        }
    }

    private fun setupUserSignatureEditText() {
        val userSignature = sharedPreferences.getString("userSignature", "") ?: ""
        binding.userSignatureEditText.setText(userSignature)

        binding.saveSignatureButton.setOnClickListener {
            val newSignature = binding.userSignatureEditText.text.toString()
            sharedPreferences.edit().putString("userSignature", newSignature).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}