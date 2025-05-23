package com.example.moodtracker.data

import java.util.UUID

data class MoodEntry(
    val id: UUID = UUID.randomUUID(),
    val mood: Int,          // 0 - smutny, 1 - przeciętny, 2 - wesoły
    val note: String,
    val category: String,
    val sleepWell: Boolean,
    val wasActive: Boolean,
    val rating: Float,      // 1-5 gwiazdek
    val isImportant: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)