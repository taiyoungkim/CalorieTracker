package com.tydev.tracker.domain.usecase

import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserInfo
import com.tydev.core.domain.preferences.UserPreferences
import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
    private val preferences: UserPreferences
) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Result {
        val allNutrients = trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val foods = entry.value
                MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }
        val totalCarbs = allNutrients.values.sumOf { it.carbs }
        val totalProtein = allNutrients.values.sumOf { it.protein }
        val totalFat = allNutrients.values.sumOf { it.fat }
        val totalCalories = allNutrients.values.sumOf { it.calories }

        val userInfo = preferences.loadUserInfo()
        val caloryGoal = dailyCaloryRequirement(userInfo)
        val carbsGoal = (caloryGoal * userInfo.carbRatio / CARBSGOAL_RATIO).roundToInt()
        val proteinGoal = (caloryGoal * userInfo.proteinRatio / PROTEINGOAL_RATIO).roundToInt()
        val fatGoal = (caloryGoal * userInfo.fatRatio / FATGOAL_RATIO).roundToInt()

        return Result(
            carbsGoal = carbsGoal,
            proteinGoal = proteinGoal,
            fatGoal = fatGoal,
            caloriesGoal = caloryGoal,
            totalCarbs = totalCarbs,
            totalProtein = totalProtein,
            totalFat = totalFat,
            totalCalories = totalCalories,
            mealNutrients = allNutrients
        )
    }

    private fun bmr(userInfo: UserInfo): Int {
        return when (userInfo.gender) {
            is Gender.Male -> {
                (
                    BMR_MALE_DEFAULT + BMR_MALE_WEIGHT * userInfo.weight +
                        BMR_MALE_HEIGHT * userInfo.height - BMR_MALE_AGE * userInfo.age
                    ).roundToInt()
            }
            is Gender.Female -> {
                (
                    BMR_FEMALE_DEFAULT + BMR_FEMALE_WEIGHT * userInfo.weight +
                        BMR_FEMALE_HEIGHT * userInfo.height - BMR_FEMALE_AGE * userInfo.age
                    ).roundToInt()
            }
        }
    }

    private fun dailyCaloryRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            is ActivityLevel.Low -> ACTIVITYLEVEL_LOW
            is ActivityLevel.Medium -> ACTIVITYLEVEL_MEDIUM
            is ActivityLevel.High -> ACTIVITYLEVEL_HIGH
        }
        val caloryExtra = when (userInfo.goalType) {
            is GoalType.LoseWeight -> LOSEWEIGHT_CALORY
            is GoalType.KeepWeight -> KEEPWEIGHT_CALORY
            is GoalType.GainWeight -> GAINWEIGHT_CALORY
        }
        return (bmr(userInfo) * activityFactor + caloryExtra).roundToInt()
    }

    data class MealNutrients(
        val carbs: Int,
        val protein: Int,
        val fat: Int,
        val calories: Int,
        val mealType: MealType
    )

    data class Result(
        val carbsGoal: Int,
        val proteinGoal: Int,
        val fatGoal: Int,
        val caloriesGoal: Int,
        val totalCarbs: Int,
        val totalProtein: Int,
        val totalFat: Int,
        val totalCalories: Int,
        val mealNutrients: Map<MealType, MealNutrients>
    )
}

const val CARBSGOAL_RATIO = 4f
const val PROTEINGOAL_RATIO = 4f
const val FATGOAL_RATIO = 9f
const val BMR_MALE_DEFAULT = 66.47f
const val BMR_MALE_WEIGHT = 13.75f
const val BMR_MALE_HEIGHT = 5f
const val BMR_MALE_AGE = 6.75f
const val BMR_FEMALE_DEFAULT = 665.09f
const val BMR_FEMALE_WEIGHT = 9.56f
const val BMR_FEMALE_HEIGHT = 1.84f
const val BMR_FEMALE_AGE = 4.67
const val ACTIVITYLEVEL_LOW = 1.2f
const val ACTIVITYLEVEL_MEDIUM = 1.3f
const val ACTIVITYLEVEL_HIGH = 1.4f
const val LOSEWEIGHT_CALORY = -500
const val KEEPWEIGHT_CALORY = 0
const val GAINWEIGHT_CALORY = 500
