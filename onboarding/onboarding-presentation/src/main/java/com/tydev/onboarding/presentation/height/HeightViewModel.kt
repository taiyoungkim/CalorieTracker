package com.tydev.onboarding.presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.core.domain.usecase.FilterOutDigitsUseCase
import com.tydev.core.ui.util.UiEvent
import com.tydev.core.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    var height by mutableStateOf(DEFAULT_HEIGHT)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= VALIDATE_HEIGHT_LENGTH) {
            this.height = filterOutDigitsUseCase(height)
        }
    }

    fun updateHeight(height: String) {
        this.height = height
    }

    fun onNextClick() = viewModelScope.launch {
        val heightNumber = height.toIntOrNull() ?: run {
            _uiEvent.send(
                UiEvent.ShowSnackbar(
                    UiText.StringResource(com.tydev.core.R.string.error_height_cant_be_empty)
                )
            )
            return@launch
        }
        userDataRepository.setHeight(heightNumber)
        _uiEvent.send(UiEvent.Success)
    }
}

const val DEFAULT_HEIGHT = "180"
const val VALIDATE_HEIGHT_LENGTH = 3
