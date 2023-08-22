package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class SetShouldShowOnboardingUseCase(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(shouldShowOnboarding: Boolean) =
        userDataRepository.setShouldShowOnboarding(shouldShowOnboarding)
}
