package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.math.roundToInt

class UpdateTrackedFoodUseCaseTest {

    @MockK
    private lateinit var trackerRepository: TrackerRepository
    private lateinit var updateTrackedFoodUseCase: UpdateTrackedFoodUseCase

    @Before
    fun before() {
        trackerRepository = mockk(relaxed = true)
        updateTrackedFoodUseCase = UpdateTrackedFoodUseCase(trackerRepository)
    }

    @Test
    fun `update tracked food should call repository updateTrackedFood`() = runTest {
        val trackedFood = TrackedFood(
            id = 1,
            name = "Apple",
            carbs = 15,
            protein = 0,
            fat = 0,
            calories = 60,
            imageUrl = "apple_image_url",
            mealType = MealType.Breakfast,
            amount = 1,
            date = LocalDate.now(),
            carbsPerGram = 0.15f,
            proteinPerGram = 0f,
            fatPerGram = 0f,
            caloriePerGram = 0.6f,
        )

        val updatedAmount = 2

        coEvery { trackerRepository.updateTrackedFood(any()) } just Runs

        updateTrackedFoodUseCase(trackedFood, updatedAmount)

        val updatedTrackedFood = trackedFood.copy(
            carbs = (trackedFood.carbsPerGram * updatedAmount).roundToInt(),
            protein = (trackedFood.proteinPerGram * updatedAmount).roundToInt(),
            fat = (trackedFood.fatPerGram * updatedAmount).roundToInt(),
            calories = (trackedFood.caloriePerGram * updatedAmount).roundToInt(),
            amount = updatedAmount,
        )

        coVerify { trackerRepository.updateTrackedFood(updatedTrackedFood) }
    }
}
