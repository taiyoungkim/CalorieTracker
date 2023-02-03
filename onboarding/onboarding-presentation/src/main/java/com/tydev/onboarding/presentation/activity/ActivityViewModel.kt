package com.tydev.onboarding.presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    var selectedActivityLevel by mutableStateOf(ActivityLevel.MEDIUM)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelSelect(activityLevel: ActivityLevel) {
        selectedActivityLevel = activityLevel
    }

    fun onNextClick() = viewModelScope.launch {
        userDataRepository.setActivityLevel(selectedActivityLevel)
        _uiEvent.send(UiEvent.Success)
    }
}
