package com.tydev.onboarding.presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.R
import com.tydev.core.domain.usecase.SetWeightUseCase
import com.tydev.core.ui.util.UiEvent
import com.tydev.core.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val setWeightUseCase: SetWeightUseCase
) : ViewModel() {

    var weight by mutableStateOf(DEFAULT_WEIGHT)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length <= VALIDATE_WEIGHT_LENGTH) {
            this.weight = weight
        }
    }

    fun updateWeight(weight: String) {
        this.weight = weight
    }

    fun onNextClick() = viewModelScope.launch {
        val weightNumber = weight.toFloatOrNull() ?: run {
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(R.string.error_weight_cant_be_empty)
                )
            )
            return@launch
        }
        setWeightUseCase(weightNumber)
        _uiEvent.send(UiEvent.Success)
    }
}

const val DEFAULT_WEIGHT = "80.0"
const val VALIDATE_WEIGHT_LENGTH = 5
