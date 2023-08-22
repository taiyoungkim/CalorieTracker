package com.tydev.calorietracker.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tydev.core.domain.model.UserData
import com.tydev.onboarding.presentation.navigation.WELCOME
import com.tydev.onboarding.presentation.navigation.activityScreen
import com.tydev.onboarding.presentation.navigation.ageScreen
import com.tydev.onboarding.presentation.navigation.genderScreen
import com.tydev.onboarding.presentation.navigation.goalScreen
import com.tydev.onboarding.presentation.navigation.heightScreen
import com.tydev.onboarding.presentation.navigation.navigateToActivity
import com.tydev.onboarding.presentation.navigation.navigateToAge
import com.tydev.onboarding.presentation.navigation.navigateToGender
import com.tydev.onboarding.presentation.navigation.navigateToGoal
import com.tydev.onboarding.presentation.navigation.navigateToHeight
import com.tydev.onboarding.presentation.navigation.navigateToNutrientGoal
import com.tydev.onboarding.presentation.navigation.navigateToWeight
import com.tydev.onboarding.presentation.navigation.nutrientGoalScreen
import com.tydev.onboarding.presentation.navigation.weightScreen
import com.tydev.onboarding.presentation.navigation.welcomeScreen
import com.tydev.tracker.presentation.navigation.TRACKER_OVERVIEW
import com.tydev.tracker.presentation.navigation.navigateToTrackerOverview
import com.tydev.tracker.presentation.navigation.searchScreen
import com.tydev.tracker.presentation.navigation.trackerOverViewScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    userData: UserData,
    snackbarHostState: SnackbarHostState,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = if (userData.shouldShowOnboarding) WELCOME else TRACKER_OVERVIEW,
        modifier = Modifier.padding(padding),
    ) {
        welcomeScreen { navController.navigateToAge() }
        ageScreen({ navController.navigateToGender() }, snackbarHostState)
        genderScreen { navController.navigateToHeight(it) }
        heightScreen({ navController.navigateToWeight(it) }, snackbarHostState)
        weightScreen({ navController.navigateToActivity() }, snackbarHostState)
        activityScreen { navController.navigateToGoal() }
        goalScreen { navController.navigateToNutrientGoal() }
        nutrientGoalScreen({ navController.navigateToTrackerOverview() }, snackbarHostState)
        trackerOverViewScreen(navController, userData)
        searchScreen(navController, snackbarHostState)
    }
}
