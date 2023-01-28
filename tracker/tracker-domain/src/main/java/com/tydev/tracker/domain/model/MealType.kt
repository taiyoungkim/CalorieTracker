package com.tydev.tracker.domain.model

sealed class MealType(val name: String) {
    object Breakfast : MealType("Breakfast")
    object Lunch : MealType("Lunch")
    object Dinner : MealType("Dinner")
    object Snack : MealType("Snack")

    companion object {
        fun fromString(name: String): MealType {
            return when (name) {
                "Breakfast" -> Breakfast
                "Lunch" -> Lunch
                "Dinner" -> Dinner
                "Snack" -> Snack
                else -> Breakfast
            }
        }
    }
}
