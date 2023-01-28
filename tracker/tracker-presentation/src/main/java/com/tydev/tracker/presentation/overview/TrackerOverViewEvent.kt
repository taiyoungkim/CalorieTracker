package com.tydev.tracker.presentation.overview

import com.tydev.tracker.domain.model.TrackedFood

sealed class TrackerOverViewEvent {
    object OnNextDayClick : TrackerOverViewEvent()
    object OnPreviousDayClick : TrackerOverViewEvent()
    data class OnToggleMealClick(val meal: Meal) : TrackerOverViewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood) : TrackerOverViewEvent()
    data class OnAddFoodClick(val meal: Meal) : TrackerOverViewEvent()
}
