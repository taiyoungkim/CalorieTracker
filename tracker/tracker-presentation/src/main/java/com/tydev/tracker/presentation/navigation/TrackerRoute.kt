package com.tydev.tracker.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed class TrackerRoute(val route: String) {
    object TrackerOverview : TrackerRoute(TRACKER_OVERVIEW)
    object Search : TrackerRoute(SEARCH)

    fun navigate(navController: NavController, navOptions: NavOptions? = null, vararg args: Any) {
        val routeWithArgs = args.joinToString(separator = "/", prefix = "/") { args -> "$args" }
        navController.navigate(route + routeWithArgs, navOptions)
    }
}
