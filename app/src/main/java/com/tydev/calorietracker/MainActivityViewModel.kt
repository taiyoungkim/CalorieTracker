package com.tydev.calorietracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.calorietracker.MainActivityUiState.Loading
import com.tydev.calorietracker.MainActivityUiState.Success
import com.tydev.core.domain.model.UserData
import com.tydev.core.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {
//    private val shouldShowOnboarding: Flow<Boolean> =
//        userDataRepository.userData.map { !it.shouldShowOnboarding }

    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
