package com.tydev.tracker.domain.usecase

import com.tydev.tracker.domain.model.TrackableFood
import com.tydev.tracker.domain.repository.TrackerRepository

class SearchFoodUseCase(
    private val repository: TrackerRepository,
) {

    suspend operator fun invoke(
        query: String,
        page: Int = DEFAULT_PAGE,
        pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Result<List<TrackableFood>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return repository.searchFood(query.trim(), page, pageSize)
    }
}

const val DEFAULT_PAGE = 1
const val DEFAULT_PAGE_SIZE = 40
