package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.Gender
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

class SetGenderUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>()
    private lateinit var setGenderUseCase: SetGenderUseCase

    @Before
    fun before() {
        setGenderUseCase = SetGenderUseCase(userDataRepository)
    }

    @Test
    fun `setGender should call UserDataRepository setGender with correct gender`() = runBlocking {
        val gender = mockk<Gender>(relaxed = true)
        coEvery { userDataRepository.setGender(gender) } just Runs

        setGenderUseCase(gender)

        coVerify { userDataRepository.setGender(gender) }
    }
}