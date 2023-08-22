package com.tydev.core.domain.usecase

import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import com.tydev.core.domain.repository.UserDataRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetUserDataUseCaseTest {

    @MockK
    lateinit var userDataRepository: UserDataRepository

    private lateinit var getUserDataUseCase: GetUserDataUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserDataUseCase = GetUserDataUseCase(userDataRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should call UserDataRepository getUserData`() = runTest {
        val expectedUserData = UserData(
            Gender.MALE,
            30,
            80f,
            180,
            ActivityLevel.MEDIUM,
            GoalType.LOSE_WEIGHT,
            0.5f,
            0.3f,
            0.2f,
            true,
        )

        val flow = flowOf(expectedUserData)

        every { userDataRepository.getUserData() } returns flow

        val actualUserData = getUserDataUseCase()

        assertEquals(expectedUserData, actualUserData.first())
    }
}
