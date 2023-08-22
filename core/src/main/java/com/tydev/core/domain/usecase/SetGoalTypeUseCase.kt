package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.repository.UserDataRepository

class SetGoalTypeUseCase(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(goalType: GoalType) = userDataRepository.setGoalType(goalType)
}
