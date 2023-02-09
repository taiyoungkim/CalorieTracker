package com.tydev.onboarding.presentation.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.core.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    var selectedGender by mutableStateOf(Gender.MALE)
        private set

    // TODO: SharedFlow Migration
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClick() = viewModelScope.launch {
        userDataRepository.setGender(selectedGender)
        _uiEvent.send(UiEvent.Success)
    }
}
