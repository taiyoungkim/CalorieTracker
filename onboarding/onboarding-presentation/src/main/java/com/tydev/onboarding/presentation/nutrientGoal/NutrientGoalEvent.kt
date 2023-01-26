package com.tydev.onboarding.presentation.nutrientGoal

sealed class NutrientGoalEvent {
    data class OnCarbRatioEnter(val ratio: String) : NutrientGoalEvent()
    data class OnProteinRatioEnter(val ratio: String) : NutrientGoalEvent()
    data class OnFatRatioEnter(val ratio: String) : NutrientGoalEvent()
    object OnNextClick : NutrientGoalEvent()
}
