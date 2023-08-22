package com.tydev.tracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedFoodEntity(
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int,
    val carbsPerGram: Float,
    val proteinPerGram: Float,
    val fatPerGram: Float,
    val caloriePerGram: Float,
    @PrimaryKey val id: Int? = null,
)
