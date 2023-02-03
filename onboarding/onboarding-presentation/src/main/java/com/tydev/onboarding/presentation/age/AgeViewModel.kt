package com.tydev.onboarding.presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.core.domain.usecase.FilterOutDigitsUseCase
import com.tydev.core.util.UiEvent
import com.tydev.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val filterOutDigitsUseCase: FilterOutDigitsUseCase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    var age by mutableStateOf(DEFAULT_AGE)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= VALIDATE_AGE_LENGTH) {
            this.age = filterOutDigitsUseCase(age)
        }
    }

    fun onNextClick() = viewModelScope.launch {
        val ageNumber = age.toIntOrNull() ?: run {
            _uiEvent.send(
                // viewModel 에 context 를 참조해야하는 문제
                // -> helper class 를 통해 해결
                UiEvent.ShowSnackbar(
                    UiText.StringResource(com.tydev.core.R.string.error_age_cant_be_empty)
                )
            )
            return@launch
        }
        userDataRepository.setAge(ageNumber)
        _uiEvent.send(UiEvent.Success)
    }
}

const val DEFAULT_AGE = "20"
const val VALIDATE_AGE_LENGTH = 3
