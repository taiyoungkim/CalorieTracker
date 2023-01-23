package com.tydev.calorietracker.navigation

import androidx.navigation.NavController
import com.tydev.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}
