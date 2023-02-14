package com.tydev.tracker.domain.di

import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.tracker.domain.repository.TrackerRepository
import com.tydev.tracker.domain.usecase.CalculateMealNutrientsUseCase
import com.tydev.tracker.domain.usecase.DeleteTrackedFoodUseCase
import com.tydev.tracker.domain.usecase.GetFoodsForDateUseCase
import com.tydev.tracker.domain.usecase.SearchFoodUseCase
import com.tydev.tracker.domain.usecase.TrackFoodUseCase
import com.tydev.tracker.domain.usecase.TrackerUseCases
import com.tydev.tracker.domain.usecase.UpdateTrackedFoodUseCase
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
        trackerRepository: TrackerRepository,
        userDataRepository: UserDataRepository
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFoodUseCase = TrackFoodUseCase(trackerRepository),
            searchFoodUseCase = SearchFoodUseCase(trackerRepository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(trackerRepository),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(trackerRepository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(userDataRepository),
            updateTrackedFoodUseCase = UpdateTrackedFoodUseCase(trackerRepository)
        )
    }
}
