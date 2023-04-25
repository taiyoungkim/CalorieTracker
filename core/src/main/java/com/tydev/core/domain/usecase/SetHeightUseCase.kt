package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class SetHeightUseCase(
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(height: Int) = userDataRepository.setHeight(height)
}
