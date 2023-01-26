package com.tydev.onboarding.presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.preferences.UserPreferences
import com.tydev.core.domain.usecase.FilterOutDigitsUseCase
import com.tydev.core.navigation.Route
import com.tydev.core.util.UiEvent
import com.tydev.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: UserPreferences,
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase
) : ViewModel() {

    var weight by mutableStateOf(DEFAULT_WEIGHT)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length <= VALIDATE_WEIGHT_LENGTH) {
            this.weight = filterOutDigitsUseCase(weight)
        }
    }

    fun onNextClick() = viewModelScope.launch {
        val weightNumber = weight.toFloatOrNull() ?: run {
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(com.tydev.core.R.string.error_weight_cant_be_empty)
                )
            )
            return@launch
        }
        preferences.saveWeight(weightNumber)
        _uiEvent.send(UiEvent.Navigate(Route.ACTIVITY))
    }
}

const val DEFAULT_WEIGHT = "80.0"
const val VALIDATE_WEIGHT_LENGTH = 5
