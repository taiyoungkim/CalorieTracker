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

class SetProteinRatioUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setProteinRatioUseCase: SetProteinRatioUseCase

    @Before
    fun before() {
        setProteinRatioUseCase = SetProteinRatioUseCase(userDataRepository)
    }

    @Test
    fun `setProteinRatio should call UserDataRepository setProteinRatio with correct proteinRatio`() = runBlocking {
        val proteinRatio = mockk<Float>(relaxed = true)
        coEvery { userDataRepository.setProteinRatio(proteinRatio) } just Runs

        setProteinRatioUseCase(proteinRatio)

        coVerify { userDataRepository.setProteinRatio(proteinRatio) }
    }
}
