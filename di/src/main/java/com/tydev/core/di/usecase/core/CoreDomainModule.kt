package com.tydev.core.di.usecase.core

import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.core.domain.usecase.GetUserDataUseCase
import com.tydev.core.domain.usecase.SetActivityLevelUseCase
import com.tydev.core.domain.usecase.SetAgeUseCase
import com.tydev.core.domain.usecase.SetCarbRatioUseCase
import com.tydev.core.domain.usecase.SetFatRatioUseCase
import com.tydev.core.domain.usecase.SetGenderUseCase
import com.tydev.core.domain.usecase.SetGoalTypeUseCase
import com.tydev.core.domain.usecase.SetHeightUseCase
import com.tydev.core.domain.usecase.SetProteinRatioUseCase
import com.tydev.core.domain.usecase.SetShouldShowOnboardingUseCase
import com.tydev.core.domain.usecase.SetWeightUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDomainModule {

    @Provides
    @Singleton
    fun provideSetActivityLevelUseCase(
        userDataRepository: UserDataRepository,
    ): SetActivityLevelUseCase = SetActivityLevelUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetAgeUseCase(
        userDataRepository: UserDataRepository,
    ): SetAgeUseCase = SetAgeUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetCarbRatioUseCase(
        userDataRepository: UserDataRepository,
    ): SetCarbRatioUseCase = SetCarbRatioUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetFatRatioUseCase(
        userDataRepository: UserDataRepository,
    ): SetFatRatioUseCase = SetFatRatioUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetGenderUseCase(
        userDataRepository: UserDataRepository,
    ): SetGenderUseCase = SetGenderUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetGoalTypeUseCase(
        userDataRepository: UserDataRepository,
    ): SetGoalTypeUseCase = SetGoalTypeUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetHeightUseCase(
        userDataRepository: UserDataRepository,
    ): SetHeightUseCase = SetHeightUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetProteinRatioUseCase(
        userDataRepository: UserDataRepository,
    ): SetProteinRatioUseCase = SetProteinRatioUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetShouldShowOnboardingUseCase(
        userDataRepository: UserDataRepository,
    ): SetShouldShowOnboardingUseCase = SetShouldShowOnboardingUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideSetWeightUseCase(
        userDataRepository: UserDataRepository,
    ): SetWeightUseCase = SetWeightUseCase(userDataRepository)

    @Provides
    @Singleton
    fun provideGetUserDataUseCase(
        userDataRepository: UserDataRepository,
    ): GetUserDataUseCase = GetUserDataUseCase(userDataRepository)
}
