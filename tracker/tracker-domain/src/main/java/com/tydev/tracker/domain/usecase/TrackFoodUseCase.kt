package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackableFood
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCase(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        val carbsPerGram = calculatePerGram(food.carbsPer100g)
        val proteinPerGram = calculatePerGram(food.proteinPer100g)
        val fatPerGram = calculatePerGram(food.fatPer100g)
        val caloriePerGram = calculatePerGram(food.caloriesPer100g)

        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = (carbsPerGram * amount).roundToInt(),
                protein = (proteinPerGram * amount).roundToInt(),
                fat = (fatPerGram * amount).roundToInt(),
                calories = (caloriePerGram * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date,
                carbsPerGram = carbsPerGram,
                proteinPerGram = proteinPerGram,
                fatPerGram = fatPerGram,
                caloriePerGram = caloriePerGram
            )
        )
    }

    private fun calculatePerGram(nutrientPer100g: Int): Float = (nutrientPer100g / RATIO_PERCENT)
}

const val RATIO_PERCENT = 100f
