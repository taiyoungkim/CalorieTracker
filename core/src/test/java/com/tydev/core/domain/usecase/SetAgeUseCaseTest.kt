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

class SetAgeUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setAgeUseCase: SetAgeUseCase

    @Before
    fun before() {
        setAgeUseCase = SetAgeUseCase(userDataRepository)
    }

    @Test
    fun `setAge should call UserDataRepository setAge with correct age`() = runBlocking {
        val age = 30
        coEvery { userDataRepository.setAge(age) } just Runs

        setAgeUseCase(age)

        coVerify { userDataRepository.setAge(age) }
    }
}
