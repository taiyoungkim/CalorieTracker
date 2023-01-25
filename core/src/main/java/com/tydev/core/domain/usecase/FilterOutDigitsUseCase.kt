package com.tydev.core.domain.usecase

class FilterOutDigitsUseCase {

    operator fun invoke(text: String): String {
        return text.filter { it.isDigit() }
    }
}
