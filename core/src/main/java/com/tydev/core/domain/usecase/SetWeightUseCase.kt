package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class SetWeightUseCase (
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(weight: Float) = userDataRepository.setWeight(weight)
}