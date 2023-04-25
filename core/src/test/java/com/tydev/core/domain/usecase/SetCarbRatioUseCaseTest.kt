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

class SetCarbRatioUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setCarbRatioUseCase: SetCarbRatioUseCase

    @Before
    fun before() {
        setCarbRatioUseCase = SetCarbRatioUseCase(userDataRepository)
    }

    @Test
    fun `setCarbRatio should call UserDataRepository setCarbRatio with correct carbRatio`() = runBlocking {
        val carbRatio = mockk<Float>(relaxed = true)
        coEvery { userDataRepository.setCarbRatio(carbRatio) } just Runs

        setCarbRatioUseCase(carbRatio)

        coVerify { userDataRepository.setCarbRatio(carbRatio) }
    }
}
