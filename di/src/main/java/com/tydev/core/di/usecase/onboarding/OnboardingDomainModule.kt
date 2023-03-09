package com.tydev.core.di.usecase.onboarding

import com.tydev.core.domain.usecase.FilterOutDigitsUseCase
import com.tydev.onboarding.domain.usecase.ValidateNutrientsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun provideValidateNutrientsUseCase(): ValidateNutrientsUseCase {
        return ValidateNutrientsUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideFilterOutDigitsUseCase(): FilterOutDigitsUseCase {
        return FilterOutDigitsUseCase()
    }
}
