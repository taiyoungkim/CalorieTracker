package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.repository.UserDataRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SetGoalTypeUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setGoalTypeUseCase: SetGoalTypeUseCase

    @Before
    fun before() {
        setGoalTypeUseCase = SetGoalTypeUseCase(userDataRepository)
    }

    @Test
    fun `setGoalType should call UserDataRepository setGoalType with correct goalType`() = runBlocking {
        val goalType = mockk<GoalType>(relaxed = true)
        coEvery { userDataRepository.setGoalType(goalType) } just Runs

        setGoalTypeUseCase(goalType)

        coVerify { userDataRepository.setGoalType(goalType) }
    }
}