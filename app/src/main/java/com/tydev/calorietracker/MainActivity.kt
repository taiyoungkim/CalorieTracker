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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.tydev.calorietracker.navigation.navigate
import com.tydev.calorietracker.ui.theme.CalorieTrackerTheme
import com.tydev.core.domain.preferences.UserPreferences
import com.tydev.core.navigation.Route
import com.tydev.onboarding.presentation.activity.ActivityScreen
import com.tydev.onboarding.presentation.age.AgeScreen
import com.tydev.onboarding.presentation.gender.GenderScreen
import com.tydev.onboarding.presentation.goal.GoalScreen
import com.tydev.onboarding.presentation.height.HeightScreen
import com.tydev.onboarding.presentation.nutrientGoal.NutrientScreen
import com.tydev.onboarding.presentation.weight.WeightScreen
import com.tydev.onboarding.presentation.welcome.WelcomeScreen
import com.tydev.tracker.presentation.overview.TrackerOverViewScreen
import com.tydev.tracker.presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
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
                            startDestination = if (shouldShowOnboarding) Route.WELCOME else Route.TRACKER_OVERVIEW,
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
                                NutrientScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNavigate = navController::navigate
                                )
                            }

                            composable(Route.ACTIVITY) {
                                ActivityScreen(
                                    onNavigate = navController::navigate
                                )
                            }

                            composable(Route.GOAL) {
                                GoalScreen(
                                    onNavigate = navController::navigate
                                )
                            }

                            composable(Route.TRACKER_OVERVIEW) {
                                TrackerOverViewScreen(
                                    onNavigate = navController::navigate
                                )
                            }

                            composable(
                                route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
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
                                )
                            ) {
                                val mealName = it.arguments?.getString("mealName")!!
                                val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                                val month = it.arguments?.getInt("month")!!
                                val year = it.arguments?.getInt("year")!!
                                SearchScreen(
                                    snackbarHostState = snackbarHostState,
                                    mealName = mealName,
                                    dayOfMonth = dayOfMonth,
                                    month = month,
                                    year = year,
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
