package com.tydev.tracker.domain.di

import com.tydev.core.domain.preferences.UserPreferences
import com.tydev.tracker.domain.repository.TrackerRepository
import com.tydev.tracker.domain.usecase.CalculateMealNutrientsUseCase
import com.tydev.tracker.domain.usecase.DeleteTrackedFoodUseCase
import com.tydev.tracker.domain.usecase.GetFoodsForDateUseCase
import com.tydev.tracker.domain.usecase.SearchFoodUseCase
import com.tydev.tracker.domain.usecase.TrackFoodUseCase
import com.tydev.tracker.domain.usecase.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: UserPreferences
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFoodUseCase = TrackFoodUseCase(repository),
            searchFoodUseCase = SearchFoodUseCase(repository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(repository),
            deleteTrackedFood = DeleteTrackedFoodUseCase(repository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences)
        )
    }
}
