package com.tydev.tracker.data.repository

import com.tydev.tracker.data.dto.Nutriments
import com.tydev.tracker.data.dto.Product
import com.tydev.tracker.data.dto.SearchDto
import com.tydev.tracker.data.entity.TrackedFoodEntity
import com.tydev.tracker.data.local.TrackerDao
import com.tydev.tracker.data.mapper.toTrackableFood
import com.tydev.tracker.data.mapper.toTrackedFood
import com.tydev.tracker.data.mapper.toTrackedFoodEntity
import com.tydev.tracker.data.remote.OpenFoodApi
import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.domain.model.TrackedFood
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class TrackerRepositoryImplTest {

    private lateinit var trackerDao: TrackerDao
    private lateinit var openFoodApi: OpenFoodApi
    private lateinit var trackerRepository: TrackerRepositoryImpl

    @Before
    fun setup() {
        trackerDao = mockk()
        openFoodApi = mockk()
        trackerRepository = TrackerRepositoryImpl(trackerDao, openFoodApi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `searchFood returns success with filtered list`() = runTest {
        // Given
        val query = "banana"
        val page = 1
        val pageSize = 10
        val searchDto = SearchDto(
            products = listOf(
                Product(
                    productName = "Banana",
                    nutriments = Nutriments(
                        energyKcal100g = 95.0,
                        carbohydrates100g = 22.5,
                        proteins100g = 1.2,
                        fat100g = 0.1
                    ),
                    imageFrontThumbUrl = "https://banana.com/image.jpg"
                ),
                Product(
                    productName = "Orange",
                    nutriments = Nutriments(
                        energyKcal100g = 89.0,
                        carbohydrates100g = 23.43,
                        proteins100g = 1.09,
                        fat100g = 0.33
                    ),
                    imageFrontThumbUrl = "https://orange.com/image2.jpg"
                )
            )
        )
        coEvery { openFoodApi.searchFood(query, page, pageSize) } returns searchDto

        // When
        val result = trackerRepository.searchFood(query, page, pageSize)

        // Then
        coVerify { openFoodApi.searchFood(query, page, pageSize) }
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
        assertEquals("Banana", result.getOrNull()?.first()?.name)
    }

    @Test
    fun `searchFood returns failure when api throws exception`() = runTest {
        // Given
        val query = "banana"
        val page = 1
        val pageSize = 10
        coEvery { openFoodApi.searchFood(query, page, pageSize) } throws Exception()

        // When
        val result = trackerRepository.searchFood(query, page, pageSize)

        // Then
        coVerify { openFoodApi.searchFood(query, page, pageSize) }
        assertTrue(result.isFailure)
    }

    @Test
    fun `insertTrackedFood inserts food to dao`() = runTest {
        // Given
        val trackedFood = mockk<TrackedFood>(relaxed = true)

        val trackedFoodEntitySlot = slot<TrackedFoodEntity>()
        coEvery { trackerDao.insertTrackedFood(capture(trackedFoodEntitySlot)) } just runs

        // When
        trackerRepository.insertTrackedFood(trackedFood)

        // Then
        coVerify { trackerDao.insertTrackedFood(any()) }
        assertEquals(trackedFood.toTrackedFoodEntity(), trackedFoodEntitySlot.captured)
    }

    @Test
    fun `deleteTrackedFood delete food to dao`() = runTest {
        // Given
        val trackedFood = mockk<TrackedFood>(relaxed = true)

        val trackedFoodEntitySlot = slot<TrackedFoodEntity>()

        // When
        coEvery { trackerRepository.deleteTrackedFood(trackedFood) } just runs
        coEvery { trackerDao.deleteTrackedFood(capture(trackedFoodEntitySlot)) } just runs
        trackerRepository.deleteTrackedFood(trackedFood)

        // Then
        val expectedEntity = trackedFood.toTrackedFoodEntity()
        assertEquals(expectedEntity, trackedFoodEntitySlot.captured)
        coVerify { trackerDao.deleteTrackedFood(expectedEntity) }
    }

    @Test
    fun `getFoodsForDate returns flow of tracked foods for given date`() = runTest {
        // Given
        val localDate = LocalDate.of(2022, 10, 15)
        val entities = listOf(
            TrackedFoodEntity(
                id = 1,
                name = "Banana",
                carbs = 23,
                protein = 1,
                fat = 0,
                calories = 89,
                imageUrl = "https://banana.com/image.jpg",
                type = MealType.Breakfast.name,
                amount = 100,
                dayOfMonth = 15,
                month = 10,
                year = 2022,
                carbsPerGram = 0.23f,
                proteinPerGram = 0.01f,
                fatPerGram = 0f,
                caloriePerGram = 0.89f
            ),
            TrackedFoodEntity(
                id = 2,
                name = "Apple",
                carbs = 25,
                protein = 1,
                fat = 0,
                calories = 95,
                imageUrl = "https://apple.com/image.jpg",
                type = MealType.Lunch.name,
                amount = 200,
                dayOfMonth = 15,
                month = 10,
                year = 2022,
                carbsPerGram = 0.25f,
                proteinPerGram = 0.01f,
                fatPerGram = 0f,
                caloriePerGram = 0.95f
            )
        )
        val expected = entities.map { it.toTrackedFood() }

        coEvery { trackerDao.getFoodsForDate(any(), any(), any()) } returns flowOf(entities)

        // When
        val actual = trackerRepository.getFoodsForDate(localDate).first()

        // Then
        assertEquals(expected, actual)
    }

    @Test
    fun `updateTrackedFood updates food in dao`() = runTest {
        // Given
        val trackedFood = mockk<TrackedFood>(relaxed = true)

        val trackedFoodEntitySlot = slot<TrackedFoodEntity>()

        // When
        coEvery { trackerRepository.updateTrackedFood(trackedFood) } just runs
        coEvery { trackerDao.updateTrackedFood(capture(trackedFoodEntitySlot)) } just runs
        trackerRepository.updateTrackedFood(trackedFood)

        // Then
        val expectedEntity = trackedFood.toTrackedFoodEntity()
        assertEquals(expectedEntity, trackedFoodEntitySlot.captured)
        coVerify { trackerDao.updateTrackedFood(trackedFood.toTrackedFoodEntity()) }
    }
}