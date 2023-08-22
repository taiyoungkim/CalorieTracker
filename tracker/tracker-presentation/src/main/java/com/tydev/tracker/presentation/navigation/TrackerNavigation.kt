package com.tydev.tracker.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.tydev.core.domain.model.UserData
import com.tydev.tracker.presentation.overview.TrackerOverViewRoute
import com.tydev.tracker.presentation.search.SearchRoute

const val TRACKER_OVERVIEW = "tracker_overview"
const val SEARCH = "search"

fun NavController.navigateToTrackerOverview(navOptions: NavOptions? = null) {
    this.navigate(TRACKER_OVERVIEW, navOptions)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.trackerOverViewScreen(navController: NavHostController, userData: UserData) {
    composable(route = TrackerRoute.TrackerOverview.route) {
        TrackerOverViewRoute(
            userData = userData,
            onNavigateToSearch = { mealName, day, month, year ->
                TrackerRoute.Search.navigate(
                    navController,
                    args = arrayOf(
                        mealName,
                        day,
                        month,
                        year,
                    ),
                )
            },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
fun NavGraphBuilder.searchScreen(navController: NavHostController, snackbarHostState: SnackbarHostState) {
    composable(
        route = TrackerRoute.Search.route + "/{mealName}/{dayOfMonth}/{month}/{year}",
        arguments = listOf(
            navArgument("mealName") {
                type = NavType.StringType
            },
            navArgument("dayOfMonth") {
                type = NavType.IntType
            },
            navArgument("month") {
                type = NavType.IntType
            },
            navArgument("year") {
                type = NavType.IntType
            },
        ),
    ) { backStackEntry ->
        val mealName = backStackEntry.arguments?.getString("mealName")!!
        val dayOfMonth = backStackEntry.arguments?.getInt("dayOfMonth")!!
        val month = backStackEntry.arguments?.getInt("month")!!
        val year = backStackEntry.arguments?.getInt("year")!!
        SearchRoute(
            snackbarHostState = snackbarHostState,
            mealName = mealName,
            dayOfMonth = dayOfMonth,
            month = month,
            year = year,
            onNavigateUp = { navController.navigateUp() },
        )
    }
}
