package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.repository.UserDataRepository

class SetGenderUseCase(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(gender: Gender) = userDataRepository.setGender(gender)
}
