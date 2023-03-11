package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.ActivityLevel
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

class SetActivityLevelUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setActivityLevelUseCase: SetActivityLevelUseCase

    @Before
    fun before() {
        setActivityLevelUseCase = SetActivityLevelUseCase(userDataRepository)
    }

    @Test
    fun `setActivityLevel should call UserDataRepository setActivityLevel with correct activityLevel`() = runBlocking {
        val activityLevel = mockk<ActivityLevel>()
        coEvery { userDataRepository.setActivityLevel(activityLevel) }just Runs

        setActivityLevelUseCase(activityLevel)

        coVerify { userDataRepository.setActivityLevel(activityLevel) }
    }
}