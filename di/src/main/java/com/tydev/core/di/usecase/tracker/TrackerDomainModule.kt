package com.tydev.core.di.usecase.tracker

import com.tydev.core.domain.usecase.GetUserDataUseCase
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
        getUserDataUseCase: GetUserDataUseCase,
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFoodUseCase = TrackFoodUseCase(trackerRepository),
            searchFoodUseCase = SearchFoodUseCase(trackerRepository),
            getFoodsForDateUseCase = GetFoodsForDateUseCase(trackerRepository),
            deleteTrackedFoodUseCase = DeleteTrackedFoodUseCase(trackerRepository),
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(getUserDataUseCase),
            updateTrackedFoodUseCase = UpdateTrackedFoodUseCase(trackerRepository),
        )
    }
}
