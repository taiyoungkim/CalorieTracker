package com.tydev.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.tydev.calorietracker.MainActivityUiState.Loading
import com.tydev.calorietracker.MainActivityUiState.Success
import com.tydev.calorietracker.navigation.Route
import com.tydev.calorietracker.ui.theme.CalorieTrackerTheme
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .map {
                        uiState = it
                    }
                    .collect()
            }
        }
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
                            startDestination = if (shouldShowOnboarding(uiState)) Route.WELCOME else Route.TRACKER_OVERVIEW,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable(Route.WELCOME) {
                                WelcomeScreen(
                                    onNextClick = {
                                        navController.navigate(Route.GENDER)
                                    }
                                )
                            }
                            composable(Route.AGE) {
                                AgeScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNextClick = {
                                        navController.navigate(Route.HEIGHT)
                                    }
                                )
                            }

                            composable(Route.GENDER) {
                                GenderScreen(
                                    onNextClick = {
                                        navController.navigate(Route.AGE)
                                    }
                                )
                            }

                            composable(Route.HEIGHT) {
                                HeightScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNextClick = {
                                        navController.navigate(Route.WEIGHT)
                                    }
                                )
                            }

                            composable(Route.WEIGHT) {
                                WeightScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNextClick = {
                                        navController.navigate(Route.ACTIVITY)
                                    }
                                )
                            }

                            composable(Route.NUTRIENT_GOAL) {
                                NutrientScreen(
                                    snackbarHostState = snackbarHostState,
                                    onNextClick = {
                                        navController.navigate(Route.TRACKER_OVERVIEW)
                                    }
                                )
                            }

                            composable(Route.ACTIVITY) {
                                ActivityScreen(
                                    onNextClick = {
                                        navController.navigate(Route.GOAL)
                                    }
                                )
                            }

                            composable(Route.GOAL) {
                                GoalScreen(
                                    onNextClick = {
                                        navController.navigate(Route.NUTRIENT_GOAL)
                                    }
                                )
                            }

                            composable(Route.TRACKER_OVERVIEW) {
                                TrackerOverViewScreen(
                                    onNavigateToSearch = { mealName, day, month, year ->
                                        navController.navigate(
                                            Route.SEARCH + "/$mealName" +
                                                "/$day" +
                                                "/$month" +
                                                "/$year"
                                        )
                                    }
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

@Composable
fun shouldShowOnboarding(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    Loading -> true
    is Success -> uiState.userData.shouldShowOnboarding
}
