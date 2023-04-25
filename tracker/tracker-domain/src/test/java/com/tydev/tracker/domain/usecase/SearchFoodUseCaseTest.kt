package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.TrackableFood
import com.tydev.tracker.domain.repository.TrackerRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchFoodUseCaseTest {

    @MockK
    private lateinit var trackerRepository: TrackerRepository
    private lateinit var searchFoodUseCase: SearchFoodUseCase

    @Before
    fun before() {
        trackerRepository = mockk(relaxed = true)
        searchFoodUseCase = SearchFoodUseCase(trackerRepository)
    }

    @Test
    fun `given a blank query, return empty list`() = runTest {
        // Given
        val query = ""

        // When
        val result = searchFoodUseCase(query)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<TrackableFood>(), result.getOrNull())
    }

    @Test
    fun `given a non-blank query, return search results`() = runTest {
        // Given
        val query = "chicken"
        val expectedFoods = listOf(
            TrackableFood(
                name = "Grilled chicken breast",
                carbsPer100g = 0,
                proteinPer100g = 31,
                fatPer100g = 3,
                caloriesPer100g = 165,
                imageUrl = "https://example.com/chicken.jpg"
            ),
            TrackableFood(
                name = "Chicken Caesar salad",
                carbsPer100g = 4,
                proteinPer100g = 17,
                fatPer100g = 14,
                caloriesPer100g = 233,
                imageUrl = "https://example.com/caesar.jpg"
            )
        )
        coEvery {
            trackerRepository.searchFood(query, DEFAULT_PAGE, DEFAULT_PAGE_SIZE)
        } returns Result.success(expectedFoods)

        // When
        val result = searchFoodUseCase(query)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedFoods, result.getOrNull())
    }
}
