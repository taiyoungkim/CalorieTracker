package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.repository.UserDataRepository

class SetActivityLevelUseCase (
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(activityLevel: ActivityLevel) = userDataRepository.setActivityLevel(activityLevel)
}