package com.tydev.core.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class FilterOutDigitsUseCaseTest {

    private val filterOutDigitsUseCase = FilterOutDigitsUseCase()

    @Test
    fun `invoke should return string without digits`() {
        // Given
        val input = "-abc123def"
        val expectedOutput = "123"

        // When
        val actualOutput = filterOutDigitsUseCase(input)

        // Then
        assertEquals(expectedOutput, actualOutput)
    }
}