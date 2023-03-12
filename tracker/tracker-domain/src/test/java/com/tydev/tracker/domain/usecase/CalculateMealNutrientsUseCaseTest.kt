package com.tydev.tracker.domain.usecase

import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import com.tydev.core.domain.usecase.GetUserDataUseCase
import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsUseCaseTest {

    private lateinit var getUserDataUseCase: GetUserDataUseCase
    private lateinit var calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase

    @Before
    fun before() {
        getUserDataUseCase = mockk()
        coEvery {
            getUserDataUseCase.invoke()
        } returns flow {
            emit(
                UserData(
                    age = 30,
                    gender = Gender.MALE,
                    weight = 70f,
                    height = 170,
                    activityLevel = ActivityLevel.MEDIUM,
                    goalType = GoalType.KEEP_WEIGHT,
                    carbRatio = 50f,
                    proteinRatio = 20f,
                    fatRatio = 30f,
                    shouldShowOnboarding = false
                )
            )
        }

        calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(getUserDataUseCase)
    }

    @Test
    fun `Calories for breakfast properly calculated`() = runTest {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                fat = Random.nextInt(100),
                protein = Random.nextInt(100),
                carbsPerGram = Random.nextFloat(),
                fatPerGram = Random.nextFloat(),
                proteinPerGram = Random.nextFloat(),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000),
                caloriePerGram = Random.nextFloat(),
                id = Random.nextInt(),
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods)

        val breakfastCalories = result.map {
            it.mealNutrients.values.filter { mealNutrients ->
                mealNutrients.mealType is MealType.Breakfast
            }.sumOf { it.calories }
        }.first()

        val expectedCalories = trackedFoods
            .filter { it.mealType is MealType.Breakfast }
            .sumOf { it.calories }

        assertEquals(breakfastCalories, expectedCalories)
    }

    @Test
    fun `Carbs for dinner properly calculated`() = runTest {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                fat = Random.nextInt(100),
                protein = Random.nextInt(100),
                carbsPerGram = Random.nextFloat(),
                fatPerGram = Random.nextFloat(),
                proteinPerGram = Random.nextFloat(),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snacks").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000),
                caloriePerGram = Random.nextFloat(),
                id = Random.nextInt(),
            )
        }

        val result = calculateMealNutrientsUseCase(trackedFoods)

        val dinnerCarbs = result.map {
            it.mealNutrients.values.filter { mealNutrients ->
                mealNutrients.mealType is MealType.Dinner
            }.sumOf { it.carbs }
        }.first()

        val expectedCarbs = trackedFoods
            .filter { it.mealType is MealType.Dinner }
            .sumOf { it.carbs }

        assertEquals(dinnerCarbs, expectedCarbs)
    }
}