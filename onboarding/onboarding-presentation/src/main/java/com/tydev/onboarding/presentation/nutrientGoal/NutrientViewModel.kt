package com.tydev.onboarding.presentation.nutrientGoal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.usecase.FilterOutDigitsUseCase
import com.tydev.core.domain.usecase.SetCarbRatioUseCase
import com.tydev.core.domain.usecase.SetFatRatioUseCase
import com.tydev.core.domain.usecase.SetProteinRatioUseCase
import com.tydev.core.ui.util.UiEvent
import com.tydev.core.ui.util.UiText
import com.tydev.onboarding.domain.usecase.ValidateNutrientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientViewModel @Inject constructor(
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase,
    private val setCarbRatioUseCase: SetCarbRatioUseCase,
    private val setProteinRatioUseCase: SetProteinRatioUseCase,
    private val setFatRatioUseCase: SetFatRatioUseCase,
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(
                    carbsRatio = filterOutDigitsUseCase(event.ratio),
                )
            }
            is NutrientGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(
                    proteinRatio = filterOutDigitsUseCase(event.ratio),
                )
            }
            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(
                    fatRatio = filterOutDigitsUseCase(event.ratio),
                )
            }
            is NutrientGoalEvent.OnNextClick -> {
                val result = validateNutrientsUseCase(
                    carbsRatioText = state.carbsRatio,
                    proteinRatioText = state.proteinRatio,
                    fatRatioText = state.fatRatio,
                )
                when (result) {
                    is ValidateNutrientsUseCase.Result.Success -> {
                        viewModelScope.launch {
                            setCarbRatioUseCase(result.carbsRatio)
                            setProteinRatioUseCase(result.proteinRatio)
                            setFatRatioUseCase(result.fatRatio)
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                    is ValidateNutrientsUseCase.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString(result.message)))
                        }
                    }
                }
            }
        }
    }
}
