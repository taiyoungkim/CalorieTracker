package com.tydev.tracker.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.usecase.SetShouldShowOnboardingUseCase
import com.tydev.tracker.domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverViewViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val setShouldShowOnboardingUseCase: SetShouldShowOnboardingUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TrackerOverViewState())
    val state: StateFlow<TrackerOverViewState> get() = _state

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
                    trackerUseCases.deleteTrackedFoodUseCase(event.trackedFood)
                }
            }
            is TrackerOverViewEvent.OnNextDayClick -> {
                _state.value = state.value.copy(
                    date = state.value.date.plusDays(1),
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnPreviousDayClick -> {
                _state.value = state.value.copy(
                    date = state.value.date.minusDays(1),
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnToggleMealClick -> {
                _state.value = state.value.copy(
                    meals = state.value.meals.map {
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
                    trackerUseCases.updateTrackedFoodUseCase(event.trackedFood, event.amount)
                }
            }
        }
    }

    private fun refreshFoods() {
        viewModelScope.launch {
            trackerUseCases.getFoodsForDateUseCase(state.value.date)
                .flatMapLatest { foods ->
                    trackerUseCases.calculateMealNutrientsUseCase(foods)
                        .map { nutrientsResult -> Pair(foods, nutrientsResult) }
                }.collect { (foods, nutrientsResult) ->
                    _state.value = state.value.copy(
                        totalCarbs = nutrientsResult.totalCarbs,
                        totalProtein = nutrientsResult.totalProtein,
                        totalFat = nutrientsResult.totalFat,
                        totalCalories = nutrientsResult.totalCalories,
                        carbsGoal = nutrientsResult.carbsGoal,
                        proteinGoal = nutrientsResult.proteinGoal,
                        fatGoal = nutrientsResult.fatGoal,
                        caloriesGoal = nutrientsResult.caloriesGoal,
                        trackedFoods = foods,
                        meals = state.value.meals.map {
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
        }
    }

    fun onItemExpanded(cardId: Int) {
        _state.value = state.value.copy(
            isReveal = cardId,
        )
    }

    fun onItemCollapsed() {
        _state.value = state.value.copy(
            isReveal = -1,
        )
    }
}
