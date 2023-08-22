package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class GetUserDataUseCase(
    private val userDataRepository: UserDataRepository,
) {
    operator fun invoke() = userDataRepository.getUserData()
}
