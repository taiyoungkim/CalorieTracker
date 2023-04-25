package com.tydev.core.datastore

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class UserPreferencesSerializerTest {
    private val userPreferencesSerializer = UserPreferencesSerializer()

    @Test
    fun defaultUserPreference_isEmpty() {
        assertEquals(
            userPreferences {
                shouldShowOnboarding = true
            },
            userPreferencesSerializer.defaultValue
        )
    }

    @Test
    fun writingAndReadingUserPreferences_outputsCorrectValue() = runTest {
        val expectedUserPreferences = userPreferences {
            shouldShowOnboarding = true
            age = 20
            activityLevel = ActivityLevelProto.HIGH
            height = 180
            gender = GenderProto.MALE
            carbRatio = 30f
            proteinRatio = 40f
            fatRatio = 30f
            weight = 70f
            goalType = GoalTypeProto.GAIN_WEIGHT
        }

        val outputStream = ByteArrayOutputStream()

        expectedUserPreferences.writeTo(outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())

        val actualUserPreferences = userPreferencesSerializer.readFrom(inputStream)

        kotlin.test.assertEquals(
            expectedUserPreferences,
            actualUserPreferences,
        )
    }

    @Test(expected = CorruptionException::class)
    fun readingInvalidUserPreferences_throwsCorruptionException() = runTest {
        userPreferencesSerializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
    }
}
