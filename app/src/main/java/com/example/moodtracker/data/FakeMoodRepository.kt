package com.example.moodtracker.data

import java.util.UUID

object FakeMoodRepository {
    val moodList = mutableListOf<MoodEntry>()

    fun addMood(entry: MoodEntry) {
        moodList.add(0, entry)
    }

    fun getMoodById(id: String?): MoodEntry? {
        return id?.let { uuid ->
            moodList.find { it.id == UUID.fromString(uuid) }
        }
    }
}