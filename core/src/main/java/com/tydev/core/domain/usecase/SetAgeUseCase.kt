package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository

class SetAgeUseCase(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(age: Int) = userDataRepository.setAge(age)
}
