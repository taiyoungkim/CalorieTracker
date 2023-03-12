package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetFoodsForDateUseCaseTest {

    private val trackerRepository = mockk<TrackerRepository>()
    private lateinit var getFoodsForDateUseCase: GetFoodsForDateUseCase

    @Before
    fun before() {
        getFoodsForDateUseCase = GetFoodsForDateUseCase(trackerRepository)
    }

    @Test
    fun `invoke should return foods for given date`() = runTest {
        // Given
        val date = LocalDate.now()
        val expectedFoods = listOf(
            TrackedFood(
                name = "Chicken Breast",
                carbs = 0,
                protein = 31,
                fat = 3,
                calories = 165,
                imageUrl = "https://example.com/chicken",
                mealType = MealType.Lunch,
                amount = 200,
                date = date,
                carbsPerGram = 0f,
                proteinPerGram = 0.155f,
                fatPerGram = 0.015f,
                caloriePerGram = 0.825f
            ),
            TrackedFood(
                name = "Broccoli",
                carbs = 2,
                protein = 3,
                fat = 0,
                calories = 30,
                imageUrl = "https://example.com/broccoli",
                mealType = MealType.Lunch,
                amount = 100,
                date = date,
                carbsPerGram = 0.02f,
                proteinPerGram = 0.03f,
                fatPerGram = 0f,
                caloriePerGram = 0.3f
            )
        )
        coEvery { trackerRepository.getFoodsForDate(date) } returns flowOf(expectedFoods)

        // When
        val actualFoods = getFoodsForDateUseCase(date).toList()

        // Then
        assertEquals(expectedFoods, actualFoods.first())
        coVerify(exactly = 1) { trackerRepository.getFoodsForDate(date) }
    }
}