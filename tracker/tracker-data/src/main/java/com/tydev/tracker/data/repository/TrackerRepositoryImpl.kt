@file:Suppress("MagicNumber")
package com.tydev.tracker.data.repository

import com.tydev.tracker.data.local.TrackerDao
import com.tydev.tracker.data.mapper.toTrackableFood
import com.tydev.tracker.data.mapper.toTrackedFood
import com.tydev.tracker.data.mapper.toTrackedFoodEntity
import com.tydev.tracker.data.remote.OpenFoodApi
import com.tydev.tracker.domain.model.TrackableFood
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDto.products
                    .filter {
                        val calculatedCalories =
                            it.nutriments.carbohydrates100g * 4f +
                                it.nutriments.proteins100g * 4f +
                                it.nutriments.fat100g * 9f
                        val lowerBound = calculatedCalories * 0.99f
                        val upperBound = calculatedCalories * 1.01f
                        it.nutriments.energyKcal100g in (lowerBound..upperBound)
                    }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }

    override suspend fun updateTrackedFood(food: TrackedFood) {
        dao.updateTrackedFood(food.toTrackedFoodEntity())
    }
}
