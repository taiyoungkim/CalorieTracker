package com.tydev.onboarding.presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.usecase.SetGoalTypeUseCase
import com.tydev.core.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val setGoalTypeUseCase: SetGoalTypeUseCase,
) : ViewModel() {

    var selectedGoal by mutableStateOf(GoalType.KEEP_WEIGHT)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeSelect(goalType: GoalType) {
        selectedGoal = goalType
    }

    fun onNextClick() = viewModelScope.launch {
        setGoalTypeUseCase(selectedGoal)
        _uiEvent.send(UiEvent.Success)
    }
}
