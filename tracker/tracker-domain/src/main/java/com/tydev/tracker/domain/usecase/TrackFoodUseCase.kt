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
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                carbs = ((food.carbsPer100g / RATIO_PERCENT) * amount).roundToInt(),
                protein = ((food.proteinPer100g / RATIO_PERCENT) * amount).roundToInt(),
                fat = ((food.fatPer100g / RATIO_PERCENT) * amount).roundToInt(),
                calories = ((food.caloriesPer100g / RATIO_PERCENT) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date,
            )
        )
    }
}

const val RATIO_PERCENT = 100f
