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

class SetHeightUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setHeightUseCase: SetHeightUseCase

    @Before
    fun before() {
        setHeightUseCase = SetHeightUseCase(userDataRepository)
    }

    @Test
    fun `setHeight should call UserDataRepository setHeight with correct height`() = runBlocking {
        val height = 180
        coEvery { userDataRepository.setHeight(height) } just Runs

        setHeightUseCase(height)

        coVerify { userDataRepository.setHeight(height) }
    }
}
