package com.tydev.core.domain.usecase

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

class SetFatRatioUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setFatRatioUseCase: SetFatRatioUseCase

    @Before
    fun before() {
        setFatRatioUseCase = SetFatRatioUseCase(userDataRepository)
    }

    @Test
    fun `setFatRatio should call UserDataRepository setFatRatio with correct fatRatio`() = runBlocking {
        val fatRatio = mockk<Float>(relaxed = true)
        coEvery { userDataRepository.setFatRatio(fatRatio) } just Runs

        setFatRatioUseCase(fatRatio)

        coVerify { userDataRepository.setFatRatio(fatRatio) }
    }
}
