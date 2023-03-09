package com.tydev.onboarding.domain.usecase

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

internal class ValidateNutrientsUseCaseTest {

    private val useCase = ValidateNutrientsUseCase()

    @Test
    fun `valid inputs return success`() {
        val result = useCase.invoke("50", "30", "20")
        assertTrue(result is ValidateNutrientsUseCase.Result.Success)
        val successResult = result as ValidateNutrientsUseCase.Result.Success
        assertEquals(0.5f, successResult.carbsRatio)
        assertEquals(0.3f, successResult.proteinRatio)
        assertEquals(0.2f, successResult.fatRatio)
    }

    @Test
    fun `invalid inputs return error`() {
        val result = useCase.invoke("50", "30", "not_a_number")
        assertTrue(result is ValidateNutrientsUseCase.Result.Error)
        val errorResult = result as ValidateNutrientsUseCase.Result.Error
        assertEquals("These are not valid values", errorResult.message)
    }

    @Test
    fun `ratios do not add up to 100% return error`() {
        val result = useCase.invoke("50", "50", "10")
        assertTrue(result is ValidateNutrientsUseCase.Result.Error)
        val errorResult = result as ValidateNutrientsUseCase.Result.Error
        assertEquals("The values must add up to 100%", errorResult.message)
    }
}