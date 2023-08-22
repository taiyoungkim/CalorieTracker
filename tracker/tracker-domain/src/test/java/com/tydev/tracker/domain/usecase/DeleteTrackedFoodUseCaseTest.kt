package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class DeleteTrackedFoodUseCaseTest {
    private val trackerRepository: TrackerRepository = mockk()
    private lateinit var deleteTrackedFoodUseCase: DeleteTrackedFoodUseCase

    @Before
    fun before() {
        deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(trackerRepository)
    }

    @Test
    fun `delete tracked food from repository`() = runTest {
        val trackedFood = TrackedFood(
            name = "Food",
            carbs = 10,
            protein = 20,
            fat = 5,
            calories = 200,
            imageUrl = "https://example.com/food.jpg",
            mealType = MealType.Lunch,
            amount = 1,
            date = LocalDate.now(),
            carbsPerGram = 1f,
            proteinPerGram = 2f,
            fatPerGram = 0.5f,
            caloriePerGram = 20f,
        )

        coEvery { trackerRepository.deleteTrackedFood(trackedFood) } just Runs

        deleteTrackedFoodUseCase(trackedFood)

        coVerify { trackerRepository.deleteTrackedFood(trackedFood) }
    }
}
