package com.tydev.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tydev.calorietracker.navigation.navigate
import com.tydev.calorietracker.ui.theme.CalorieTrackerTheme
import com.tydev.core.navigation.Route
import com.tydev.onboarding.presentation.age.AgeScreen
import com.tydev.onboarding.presentation.gender.GenderScreen
import com.tydev.onboarding.presentation.height.HeightScreen
import com.tydev.onboarding.presentation.weight.WeightScreen
import com.tydev.onboarding.presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    content = { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = Route.WELCOME,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable(Route.WELCOME) {
                                WelcomeScreen(onNavigate = navController::navigate)
                            }
                            composable(Route.AGE) {
                                AgeScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNavigate = navController::navigate
                                )
                            }

                            composable(Route.GENDER) {
                                GenderScreen(onNavigate = navController::navigate)
                            }

                            composable(Route.HEIGHT) {
                                HeightScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNavigate = navController::navigate
                                )
                            }

                            composable(Route.WEIGHT) {
                                WeightScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNavigate = navController::navigate
                                )
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
                )
            }
        }
    }
}
