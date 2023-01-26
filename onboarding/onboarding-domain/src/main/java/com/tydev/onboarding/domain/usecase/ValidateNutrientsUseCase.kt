package com.tydev.onboarding.domain.usecase

import com.tydev.core.R
import com.tydev.core.util.UiText

class ValidateNutrientsUseCase {

    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioText: String
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_invalid_values)
            )
        }
        if (carbsRatio + proteinRatio + fatRatio != RATIO_SUM) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_not_100_percent)
            )
        }
        return Result.Success(
            carbsRatio / RATIO_PERCENT,
            proteinRatio / RATIO_PERCENT,
            fatRatio / RATIO_PERCENT
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()

        // without having context
        data class Error(val message: UiText) : Result()
    }
}

const val RATIO_SUM = 100
const val RATIO_PERCENT = 100f
