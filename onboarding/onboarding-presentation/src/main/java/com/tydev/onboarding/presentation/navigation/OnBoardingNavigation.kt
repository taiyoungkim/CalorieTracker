package com.tydev.onboarding.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tydev.onboarding.presentation.activity.ActivityRoute
import com.tydev.onboarding.presentation.age.AgeRoute
import com.tydev.onboarding.presentation.gender.GenderRoute
import com.tydev.onboarding.presentation.goal.GoalRoute
import com.tydev.onboarding.presentation.height.HeightRoute
import com.tydev.onboarding.presentation.nutrientGoal.NutrientRoute
import com.tydev.onboarding.presentation.weight.WeightRoute
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
        ActivityRoute(
            onNextClick = {
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
        AgeRoute(
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

fun NavGraphBuilder.genderScreen(onNextClick: (String) -> Unit) {
    composable(route = GENDER) {
        GenderRoute(
            onNextClick = {
                onNextClick(it.name)
            }
        )
    }
}

fun NavController.navigateToGoal(navOptions: NavOptions? = null) {
    this.navigate(GOAL, navOptions)
}

fun NavGraphBuilder.goalScreen(onNextClick: () -> Unit) {
    composable(route = GOAL) {
        GoalRoute(
            onNextClick = {
                onNextClick()
            }
        )
    }
}

fun NavController.navigateToHeight(gender: String, navOptions: NavOptions? = null) {
    this.navigate("$HEIGHT?gender=$gender", navOptions)
}

fun NavGraphBuilder.heightScreen(onNextClick: (String) -> Unit, snackbarHostState: SnackbarHostState) {
    composable(route = "$HEIGHT?gender={gender}") { backStackEntry ->
        val gender = backStackEntry.arguments?.getString("gender") ?: ""
        HeightRoute(
            gender = gender,
            onNextClick = {
                onNextClick(gender)
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
        NutrientRoute(
            onNextClick = {
                onNextClick()
            },
            snackbarHostState = snackbarHostState
        )
    }
}

fun NavController.navigateToWeight(gender: String, navOptions: NavOptions? = null) {
    this.navigate("$WEIGHT?gender=$gender", navOptions)
}

fun NavGraphBuilder.weightScreen(onNextClick: () -> Unit, snackbarHostState: SnackbarHostState) {
    composable(route = "$WEIGHT?gender={gender}") { backStackEntry ->
        val gender = backStackEntry.arguments?.getString("gender") ?: ""
        WeightRoute(
            gender = gender,
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
