package com.tydev.tracker.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tydev.core.domain.repository.UserDataRepository
import com.tydev.tracker.domain.usecase.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverViewViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    userDataRepository: UserDataRepository
) : ViewModel() {

    var state by mutableStateOf(TrackerOverViewState())
        private set

    private var getFoodsForDateJob: Job? = null

    init {
        refreshFoods()
        viewModelScope.launch {
            userDataRepository.setShouldShowOnboarding(false)
        }
    }

    fun onEvent(event: TrackerOverViewEvent) {
        when (event) {
            is TrackerOverViewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteTrackedFoodUseCase(event.trackedFood)
                    refreshFoods()
                }
            }
            is TrackerOverViewEvent.OnNextDayClick -> {
                state = state.copy(
                    date = state.date.plusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnPreviousDayClick -> {
                state = state.copy(
                    date = state.date.minusDays(1)
                )
                refreshFoods()
            }
            is TrackerOverViewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is TrackerOverViewEvent.OnUpdateTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.updateTrackedFoodUseCase(event.trackedFood, 100)
                    refreshFoods()
                }
            }
        }
    }

    private fun refreshFoods() {
        // start the new job(cancel the old one)
        getFoodsForDateJob?.cancel()
        getFoodsForDateJob = trackerUseCases
            .getFoodsForDateUseCase(state.date)
            .onEach { foods ->
                trackerUseCases.calculateMealNutrientsUseCase(foods).onEach { nutrientsResult ->
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
                                        calories = 0
                                    )
                            it.copy(
                                carbs = nutrientsForMeal.carbs,
                                protein = nutrientsForMeal.protein,
                                fat = nutrientsForMeal.fat,
                                calories = nutrientsForMeal.calories
                            )
                        }
                    )
                }.launchIn(viewModelScope)
            }.launchIn(viewModelScope)
    }
}
