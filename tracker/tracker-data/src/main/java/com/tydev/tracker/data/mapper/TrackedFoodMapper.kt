package com.tydev.tracker.data.mapper

import com.tydev.tracker.data.entity.TrackedFoodEntity
import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        calories = calories,
        carbsPerGram = carbsPerGram,
        proteinPerGram = proteinPerGram,
        fatPerGram = fatPerGram,
        caloriePerGram = caloriePerGram,
        id = id,
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = calories,
        carbsPerGram = carbsPerGram,
        proteinPerGram = proteinPerGram,
        fatPerGram = fatPerGram,
        caloriePerGram = caloriePerGram,
        id = id,
    )
}
