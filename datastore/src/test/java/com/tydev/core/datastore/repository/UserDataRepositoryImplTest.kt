package com.tydev.core.datastore.repository

import com.tydev.core.datastore.UserPreferencesDataSource
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserDataRepositoryImplTest {

    private lateinit var userPreferencesDataSource: UserPreferencesDataSource
    private lateinit var userDataRepositoryImpl: UserDataRepositoryImpl

    @Before
    fun setup() {
        userPreferencesDataSource = mockk(relaxed = true)
        userDataRepositoryImpl = UserDataRepositoryImpl(userPreferencesDataSource)
    }

    @Test
    fun `getUserData returns flow of user data from data source`() = runTest {
        // Given
        val userData = mockk<UserData>()
        val flow = flowOf(userData)
        every { userPreferencesDataSource.userData } returns flow

        // When
        val result = userDataRepositoryImpl.getUserData().toList()

        // Then
        assertEquals(listOf(userData), result)
    }

    @Test
    fun `setGender sets gender in data source`() = runTest {
        // Given
        val gender = Gender.MALE
        coEvery { userPreferencesDataSource.setGender(gender) } just runs

        // When
        userDataRepositoryImpl.setGender(gender)

        // Then
        coVerify { userPreferencesDataSource.setGender(gender) }
    }

    @Test
    fun `setAge sets age in data source`() = runTest {
        // Given
        val age = 30
        coEvery { userPreferencesDataSource.setAge(age) } just runs

        // When
        userDataRepositoryImpl.setAge(age)

        // Then
        coVerify { userPreferencesDataSource.setAge(age) }
    }

    @Test
    fun `setWeight sets weight in data source`() = runTest {
        // Given
        val weight = 70f
        coEvery { userPreferencesDataSource.setWeight(weight) } just runs

        // When
        userDataRepositoryImpl.setWeight(weight)

        // Then
        coVerify { userPreferencesDataSource.setWeight(weight) }
    }

    @Test
    fun `setHeight sets height in data source`() = runTest {
        // Given
        val height = 180
        coEvery { userPreferencesDataSource.setHeight(height) } just runs

        // When
        userDataRepositoryImpl.setHeight(height)

        // Then
        coVerify { userPreferencesDataSource.setHeight(height) }
    }

    @Test
    fun `setActivityLevel sets activity level in data source`() = runTest {
        // Given
        val activityLevel = ActivityLevel.LOW
        coEvery { userPreferencesDataSource.setActivityLevel(activityLevel) } just runs

        // When
        userDataRepositoryImpl.setActivityLevel(activityLevel)

        // Then
        coVerify { userPreferencesDataSource.setActivityLevel(activityLevel) }
    }

    @Test
    fun `setGoalType sets goal type in data source`() = runTest {
        // Given
        val goalType = GoalType.GAIN_WEIGHT
        coEvery { userPreferencesDataSource.setGoalType(goalType) } just runs

        // When
        userDataRepositoryImpl.setGoalType(goalType)

        // Then
        coVerify { userPreferencesDataSource.setGoalType(goalType) }
    }

    @Test
    fun `setCarbRatio sets carb ratio in data source`() = runTest {
        // Given
        val carbRatio = 0.5f
        coEvery { userPreferencesDataSource.setCarbRatio(carbRatio) } just runs

        // When
        userDataRepositoryImpl.setCarbRatio(carbRatio)

        // Then
        coVerify { userPreferencesDataSource.setCarbRatio(carbRatio) }
    }

    @Test
    fun `setProteinRatio sets protein ratio in data source`() = runTest {
        // Given
        val proteinRatio = 0.5f
        coEvery { userPreferencesDataSource.setProteinRatio(proteinRatio) } just runs

        // When
        userDataRepositoryImpl.setProteinRatio(proteinRatio)

        // Then
        coVerify { userPreferencesDataSource.setProteinRatio(proteinRatio) }
    }

    @Test
    fun `setFatRatio sets fat ratio to user preferences data source`() = runTest {
        // Given
        val ratio = 0.5f

        // When
        coEvery { userPreferencesDataSource.setFatRatio(ratio) } just runs
        userDataRepositoryImpl.setFatRatio(ratio)

        // Then
        coVerify { userPreferencesDataSource.setFatRatio(ratio) }
    }

    @Test
    fun `setShouldShowOnboarding sets should show onboarding to user preferences data source`() = runTest {
        // Given
        val shouldShowOnboarding = true

        // When
        coEvery { userPreferencesDataSource.setShouldShowOnboarding(shouldShowOnboarding) } just runs
        userDataRepositoryImpl.setShouldShowOnboarding(shouldShowOnboarding)

        // Then
        coVerify { userPreferencesDataSource.setShouldShowOnboarding(shouldShowOnboarding) }
    }
}