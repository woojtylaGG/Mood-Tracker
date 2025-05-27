package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodtracker.adapter.MoodAdapter
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.databinding.FragmentMoodHistoryBinding

class MoodHistoryFragment : Fragment() {

    private var _binding: FragmentMoodHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val moodList = FakeMoodRepository.moodList
        val adapter = MoodAdapter(moodList) { moodEntry ->
            val action = MoodHistoryFragmentDirections
                .actionMoodHistoryFragmentToMoodDetailsFragment(moodEntry.id.toString())
            findNavController().navigate(action)
        }
        binding.moodRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.moodRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        (binding.moodRecyclerView.adapter as? MoodAdapter)?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
