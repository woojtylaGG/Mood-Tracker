package com.example.moodtracker.data

import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MoodEntry(
    val id: UUID = UUID.randomUUID(),
    val mood: Int,
    val note: String,
    val category: String,
    val sleepWell: Boolean,
    val wasActive: Boolean,
    val rating: Float,
    val isImportant: Boolean,
    val timestamp: Long = System.currentTimeMillis()
) {
    val date: String
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp))
}