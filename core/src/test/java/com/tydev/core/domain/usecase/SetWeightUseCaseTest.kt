package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetWeightUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setWeightUseCase: SetWeightUseCase

    @Before
    fun before() {
        setWeightUseCase = SetWeightUseCase(userDataRepository)
    }

    @Test
    fun `setWeight should call UserDataRepository setWeight with correct weight`() = runTest {
        val weight = mockk<Float>(relaxed = true)

        coEvery { userDataRepository.setWeight(weight) } just Runs

        setWeightUseCase(weight)

        coVerify { userDataRepository.setWeight(weight) }
    }
}