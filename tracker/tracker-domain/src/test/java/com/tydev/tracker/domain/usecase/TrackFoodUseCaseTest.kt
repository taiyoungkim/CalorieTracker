package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackableFood
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCaseTest {

    @MockK
    private val trackerRepository = mockk<TrackerRepository>(relaxed = true)
    private lateinit var trackFoodUseCase: TrackFoodUseCase

    @Before
    fun before() {
        trackFoodUseCase = TrackFoodUseCase(trackerRepository)
    }

    @Test
    fun `should track food successfully`() = runTest {
        // given
        val food = TrackableFood(
            name = "Apple",
            carbsPer100g = 14,
            proteinPer100g = 0,
            fatPer100g = 0,
            caloriesPer100g = 52,
            imageUrl = null
        )
        val amount = 200
        val mealType = mockk<MealType>(relaxed = true)
        val date = LocalDate.now()

        // when
        trackFoodUseCase(food, amount, mealType, date)

        // then
        coVerify(exactly = 1) {
            trackerRepository.insertTrackedFood(
                TrackedFood(
                    name = food.name,
                    carbs = (food.carbsPer100g * amount / RATIO_PERCENT).roundToInt(),
                    protein = (food.proteinPer100g * amount / RATIO_PERCENT).roundToInt(),
                    fat = (food.fatPer100g * amount / RATIO_PERCENT).roundToInt(),
                    calories = (food.caloriesPer100g * amount / RATIO_PERCENT).roundToInt(),
                    imageUrl = food.imageUrl,
                    mealType = mealType,
                    amount = amount,
                    date = date,
                    carbsPerGram = food.carbsPer100g / RATIO_PERCENT,
                    proteinPerGram = food.proteinPer100g / RATIO_PERCENT,
                    fatPerGram = food.fatPer100g / RATIO_PERCENT,
                    caloriePerGram = food.caloriesPer100g / RATIO_PERCENT
                )
            )
        }
    }
}