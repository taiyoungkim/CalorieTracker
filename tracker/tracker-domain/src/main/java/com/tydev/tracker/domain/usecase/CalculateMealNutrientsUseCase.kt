package com.tydev.tracker.domain.usecase

import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import com.tydev.core.domain.usecase.GetUserDataUseCase
import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
    private val getUserDataUseCase: GetUserDataUseCase
) {

    operator fun invoke(trackedFoods: List<TrackedFood>): Flow<Result> =
        getUserDataUseCase().map { userData ->
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

            val calorieGoal = dailyCaloryRequirement(userData)
            val carbsGoal = (calorieGoal * userData.carbRatio / CARBSGOAL_RATIO).roundToInt()
            val proteinGoal = (calorieGoal * userData.proteinRatio / PROTEINGOAL_RATIO).roundToInt()
            val fatGoal = (calorieGoal * userData.fatRatio / FATGOAL_RATIO).roundToInt()

            Result(
                carbsGoal = carbsGoal,
                proteinGoal = proteinGoal,
                fatGoal = fatGoal,
                caloriesGoal = calorieGoal,
                totalCarbs = totalCarbs,
                totalProtein = totalProtein,
                totalFat = totalFat,
                totalCalories = totalCalories,
                mealNutrients = allNutrients
            )
        }

    private fun bmr(userData: UserData): Int {
        return when (userData.gender) {
            Gender.MALE -> {
                (
                    BMR_MALE_DEFAULT + BMR_MALE_WEIGHT * userData.weight +
                        BMR_MALE_HEIGHT * userData.height - BMR_MALE_AGE * userData.age
                    ).roundToInt()
            }
            Gender.FEMALE -> {
                (
                    BMR_FEMALE_DEFAULT + BMR_FEMALE_WEIGHT * userData.weight +
                        BMR_FEMALE_HEIGHT * userData.height - BMR_FEMALE_AGE * userData.age
                    ).roundToInt()
            }
        }
    }

    private fun dailyCaloryRequirement(userData: UserData): Int {
        val activityFactor = when (userData.activityLevel) {
            ActivityLevel.LOW -> ACTIVITYLEVEL_LOW
            ActivityLevel.MEDIUM -> ACTIVITYLEVEL_MEDIUM
            ActivityLevel.HIGH -> ACTIVITYLEVEL_HIGH
        }
        val caloryExtra = when (userData.goalType) {
            GoalType.LOSE_WEIGHT -> LOSEWEIGHT_CALORY
            GoalType.KEEP_WEIGHT -> KEEPWEIGHT_CALORY
            GoalType.GAIN_WEIGHT -> GAINWEIGHT_CALORY
        }
        return (bmr(userData) * activityFactor + caloryExtra).roundToInt()
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
