package com.tydev.onboarding.domain.usecase

class ValidateNutrientsUseCase {

    operator fun invoke(
        carbsRatioText: String,
        proteinRatioText: String,
        fatRatioText: String,
    ): Result {
        val carbsRatio = carbsRatioText.toIntOrNull()
        val proteinRatio = proteinRatioText.toIntOrNull()
        val fatRatio = fatRatioText.toIntOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null) {
            return Result.Error(
                message = "These are not valid values",
            )
        }
        if (carbsRatio + proteinRatio + fatRatio != RATIO_SUM) {
            return Result.Error(
                message = "The values must add up to 100%",
            )
        }
        return Result.Success(
            carbsRatio / RATIO_PERCENT,
            proteinRatio / RATIO_PERCENT,
            fatRatio / RATIO_PERCENT,
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float,
        ) : Result()

        // without having context
        data class Error(val message: String) : Result()
    }
}

const val RATIO_SUM = 100
const val RATIO_PERCENT = 100f
