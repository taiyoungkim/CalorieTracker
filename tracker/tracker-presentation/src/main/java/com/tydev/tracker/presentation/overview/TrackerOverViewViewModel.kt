package com.tydev.tracker.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.usecase.SetShouldShowOnboardingUseCase
import com.tydev.tracker.domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverViewViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val setShouldShowOnboardingUseCase: SetShouldShowOnboardingUseCase,
) : ViewModel() {

    var state by mutableStateOf(TrackerOverViewState())
        private set

    private var getFoodsForDateJob: Job? = null

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    init {
        refreshFoods()
        viewModelScope.launch {
            setShouldShowOnboardingUseCase(false)
        }
    }

    fun onEvent(event: TrackerOverViewEvent) {
        when (event) {
            is TrackerOverViewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    event.trackedFood.id?.let { onItemCollapsed(it) }
                    trackerUseCases.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }
            is TrackerOverViewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1),
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1),
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else {
                            it
                        }
                    },
                )
            }
            is TrackerOverViewEvent.OnUpdateTrackedFoodClick -> {
                viewModelScope.launch {
                    event.trackedFood.id?.let { onItemCollapsed(it) }
                    trackerUseCases.updateTrackedFoodUseCase(event.trackedFood, event.amount)
                    refreshFoods()
                }
            }
        }
    }

    private fun refreshFoods() {
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases
            .getFoodsForDateUseCase(state.date)
            .flatMapLatest { foods ->
                trackerUseCases.calculateMealNutrientsUseCase(foods)
                    .map { nutrientsResult -> Pair(foods, nutrientsResult) }
            }
            .onEach { (foods, nutrientsResult) ->
                state = state.copy(
                    totalCarbs = nutrientsResult.totalCarbs,
                    totalProtein = nutrientsResult.totalProtein,
                    totalFat = nutrientsResult.totalFat,
                    totalCalories = nutrientsResult.totalCalories,
                    carbsGoal = nutrientsResult.carbsGoal,
                    proteinGoal = nutrientsResult.proteinGoal,
                    fatGoal = nutrientsResult.fatGoal,
                    caloriesGoal = nutrientsResult.caloriesGoal,
                    trackedFoods = foods,
                    meals = state.meals.map {
                        val nutrientsForMeal =
                            nutrientsResult.mealNutrients[it.mealType]
                                ?: return@map it.copy(
                                    carbs = 0,
                                    protein = 0,
                                    fat = 0,
                                    calories = 0,
                                )
                        it.copy(
                            carbs = nutrientsForMeal.carbs,
                            protein = nutrientsForMeal.protein,
                            fat = nutrientsForMeal.fat,
                            calories = nutrientsForMeal.calories,
                        )
                    },
                )
            }
            .launchIn(viewModelScope)
    }

    fun onItemExpanded(cardId: Int) {
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    fun onItemCollapsed(cardId: Int) {
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }
}
