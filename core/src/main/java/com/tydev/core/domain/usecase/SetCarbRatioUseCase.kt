package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class SetCarbRatioUseCase(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(ratio: Float) = userDataRepository.setCarbRatio(ratio)
}
