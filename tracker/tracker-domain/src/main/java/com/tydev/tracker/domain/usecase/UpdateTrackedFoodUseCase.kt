package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import kotlin.math.roundToInt

class UpdateTrackedFoodUseCase(
    private val repository: TrackerRepository,
) {
    suspend operator fun invoke(
        trackedFood: TrackedFood,
        amount: Int,
    ) {
        repository.updateTrackedFood(
            TrackedFood(
                id = trackedFood.id,
                name = trackedFood.name,
                carbs = (trackedFood.carbsPerGram * amount).roundToInt(),
                protein = (trackedFood.proteinPerGram * amount).roundToInt(),
                fat = (trackedFood.fatPerGram * amount).roundToInt(),
                calories = (trackedFood.caloriePerGram * amount).roundToInt(),
                imageUrl = trackedFood.imageUrl,
                mealType = trackedFood.mealType,
                amount = amount,
                date = trackedFood.date,
                carbsPerGram = trackedFood.carbsPerGram,
                proteinPerGram = trackedFood.proteinPerGram,
                fatPerGram = trackedFood.fatPerGram,
                caloriePerGram = trackedFood.caloriePerGram,
            ),
        )
    }
}
