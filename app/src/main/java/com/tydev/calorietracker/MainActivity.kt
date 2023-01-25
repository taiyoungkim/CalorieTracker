package com.tydev.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tydev.calorietracker.navigation.navigate
import com.tydev.calorietracker.ui.theme.CalorieTrackerTheme
import com.tydev.core.navigation.Route
import com.tydev.onboarding.presentation.gender.GenderScreen
import com.tydev.onboarding.presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.WELCOME
                ) {
                    composable(Route.WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }
                    composable(Route.AGE) {
                    }

                    composable(Route.GENDER) {
                        GenderScreen(onNavigate = navController::navigate)
                    }

                    composable(Route.HEIGHT) {
                    }

                    composable(Route.WEIGHT) {
                    }

                    composable(Route.NUTRIENT_GOAL) {
                    }

                    composable(Route.ACTIVITY) {
                    }

                    composable(Route.GOAL) {
                    }

                    composable(Route.TRACKER_OVERVIEW) {
                    }

                    composable(Route.SEARCH) {
                    }
                }
            }
        }
    }
}
