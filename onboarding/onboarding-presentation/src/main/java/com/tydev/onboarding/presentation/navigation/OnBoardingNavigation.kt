package com.tydev.onboarding.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tydev.onboarding.presentation.activity.ActivityScreen
import com.tydev.onboarding.presentation.age.AgeScreen
import com.tydev.onboarding.presentation.gender.GenderScreen
import com.tydev.onboarding.presentation.goal.GoalScreen
import com.tydev.onboarding.presentation.height.HeightScreen
import com.tydev.onboarding.presentation.nutrientGoal.NutrientScreen
import com.tydev.onboarding.presentation.weight.WeightScreen
import com.tydev.onboarding.presentation.welcome.WelcomeScreen

const val WELCOME = "welcome"
const val AGE = "age"
const val GENDER = "gender"
const val HEIGHT = "height"
const val WEIGHT = "weight"
const val NUTRIENT_GOAL = "nutrient_goal"
const val ACTIVITY = "activity"
const val GOAL = "goal"

fun NavController.navigateToActivity(navOptions: NavOptions? = null) {
    this.navigate(ACTIVITY, navOptions)
}

fun NavGraphBuilder.activityScreen(onNextClick: (String) -> Unit) {
    composable(route = ACTIVITY) {
        ActivityScreen(
            onNextClick = {
//                navController.navigate(Route.Goal.route)
                onNextClick("")
            }
        )
    }
}

fun NavController.navigateToAge(navOptions: NavOptions? = null) {
    this.navigate(AGE, navOptions)
}

fun NavGraphBuilder.ageScreen(onNextClick: () -> Unit, snackbarHostState: SnackbarHostState) {
    composable(route = AGE) {
        AgeScreen(
            onNextClick = {
                onNextClick()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavController.navigateToGender(navOptions: NavOptions? = null) {
    this.navigate(GENDER, navOptions)
}

fun NavGraphBuilder.genderScreen(onNextClick: () -> Unit) {
    composable(route = GENDER) {
        GenderScreen(
            onNextClick = {
                onNextClick()
            }
        )
    }
}

fun NavController.navigateToGoal(navOptions: NavOptions? = null) {
    this.navigate(GOAL, navOptions)
}

fun NavGraphBuilder.goalScreen(onNextClick: () -> Unit) {
    composable(route = GOAL) {
        GoalScreen(
            onNextClick = {
                onNextClick()
            }
        )
    }
}

fun NavController.navigateToHeight(navOptions: NavOptions? = null) {
    this.navigate(HEIGHT, navOptions)
}

fun NavGraphBuilder.heightScreen(onNextClick: () -> Unit, snackbarHostState: SnackbarHostState) {
    composable(route = HEIGHT) {
        HeightScreen(
            onNextClick = {
                onNextClick()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavController.navigateToNutrientGoal(navOptions: NavOptions? = null) {
    this.navigate(NUTRIENT_GOAL, navOptions)
}

fun NavGraphBuilder.nutrientGoalScreen(onNextClick: () -> Unit, snackbarHostState: SnackbarHostState) {
    composable(route = NUTRIENT_GOAL) {
        NutrientScreen(
            onNextClick = {
                onNextClick()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavController.navigateToWeight(navOptions: NavOptions? = null) {
    this.navigate(WEIGHT, navOptions)
}

fun NavGraphBuilder.weightScreen(onNextClick: () -> Unit, snackbarHostState: SnackbarHostState) {
    composable(route = WEIGHT) {
        WeightScreen(
            onNextClick = {
                onNextClick()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavController.navigateToWelcome(navOptions: NavOptions? = null) {
    this.navigate(WELCOME, navOptions)
}

fun NavGraphBuilder.welcomeScreen(onNextClick: () -> Unit) {
    composable(route = WELCOME) {
        WelcomeScreen(
            onNextClick = {
                onNextClick()
            }
        )
    }
}