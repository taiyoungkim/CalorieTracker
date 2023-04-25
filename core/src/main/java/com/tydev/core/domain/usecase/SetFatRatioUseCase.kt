package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class SetFatRatioUseCase(
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(ratio: Float) = userDataRepository.setFatRatio(ratio)
}
