package com.tydev.core.domain.usecase

import com.tydev.core.domain.repository.UserDataRepository
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetShouldShowOnboardingUseCaseTest {

    @MockK
    private val userDataRepository = mockk<UserDataRepository>(relaxed = true)
    private lateinit var setShouldShowOnboardingUseCase: SetShouldShowOnboardingUseCase

    @Before
    fun before() {
        setShouldShowOnboardingUseCase = SetShouldShowOnboardingUseCase(userDataRepository)
    }

    @Test
    fun `setShouldShowOnboarding should call UserDataRepository setShouldShowOnboarding with correct value`() = runTest {
            val shouldShowOnboarding = true

            setShouldShowOnboardingUseCase(shouldShowOnboarding)

            coVerify { userDataRepository.setShouldShowOnboarding(shouldShowOnboarding) }
        }

}