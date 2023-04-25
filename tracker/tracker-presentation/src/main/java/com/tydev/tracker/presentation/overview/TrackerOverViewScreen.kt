package com.tydev.tracker.presentation.overview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.annotation.ExperimentalCoilApi
import com.tydev.core.R
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.theme.CalorieTrackerTheme
import com.tydev.tracker.domain.model.TrackedFood
import com.tydev.tracker.presentation.overview.components.ActionsRow
import com.tydev.tracker.presentation.overview.components.AddButton
import com.tydev.tracker.presentation.overview.components.DaySelector
import com.tydev.tracker.presentation.overview.components.DraggableCardComplex
import com.tydev.tracker.presentation.overview.components.EditDialog
import com.tydev.tracker.presentation.overview.components.ExpandableMeal
import com.tydev.tracker.presentation.overview.components.NutrientsHeader
import com.tydev.tracker.presentation.overview.components.TopHeader

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@Composable
fun TrackerOverViewRoute(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    userData: UserData,
    viewModel: TrackerOverViewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val revealedCardIds by viewModel.revealedCardIdsList.collectAsStateWithLifecycle()

    TrackerOverViewScreen(
        onNavigateToSearch = onNavigateToSearch,
        userData = userData,
        state = state,
        revealedCardIds = revealedCardIds,
        onEvent = viewModel::onEvent,
        onItemExpanded = viewModel::onItemExpanded,
        onItemCollapsed = viewModel::onItemCollapsed,
    )
}

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@Composable
fun TrackerOverViewScreen(
    onNavigateToSearch: (String, Int, Int, Int) -> Unit,
    userData: UserData,
    state: TrackerOverViewState,
    revealedCardIds: List<Int>,
    onEvent: (TrackerOverViewEvent) -> Unit,
    onItemExpanded: (Int) -> Unit,
    onItemCollapsed: (Int) -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }
    val selectedFood: MutableState<TrackedFood?> = remember { mutableStateOf(null) }

    if (showDialog.value && selectedFood.value != null)
        EditDialog(
            trackedFood = selectedFood.value!!,
            setShowDialog = {
                showDialog.value = it
            }
        ) {
            onEvent(
                TrackerOverViewEvent
                    .OnUpdateTrackedFoodClick(selectedFood.value!!, it.toInt())
            )
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            TopHeader(
                goalType = userData.goalType,
                activityLevel = userData.activityLevel
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    onEvent(TrackerOverViewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    onEvent(TrackerOverViewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            NutrientsHeader(state = state)
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    onEvent(TrackerOverViewEvent.OnToggleMealClick(meal))
                },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        val foods = state.trackedFoods.filter {
                            it.mealType == meal.mealType
                        }
                        foods.forEach { food ->
                            Box(Modifier.fillMaxWidth()) {
                                ActionsRow(
                                    onDelete = {
                                        onEvent(
                                            TrackerOverViewEvent
                                                .OnDeleteTrackedFoodClick(food)
                                        )
                                    },
                                    onEdit = {
                                        selectedFood.value = food
                                        showDialog.value = true
                                    },
                                )
                                DraggableCardComplex(
                                    trackedFood = food,
                                    isRevealed = revealedCardIds.contains(food.id),
                                    cardOffset = 300f,
                                    onExpand = { onItemExpanded(food.id!!) },
                                    onCollapse = { onItemCollapsed(food.id!!) },
                                )
                            }
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }
                        AddButton(
                            text = stringResource(
                                id = R.string.add_meal,
                                meal.name.asString(context)
                            ),
                            onClick = {
                                onNavigateToSearch(
                                    meal.name.asString(context),
                                    state.date.dayOfMonth,
                                    state.date.monthValue,
                                    state.date.year
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalCoilApi
@Preview(showBackground = true, name = "TrackerOverViewPreView")
@Composable
fun TrackerOverViewPreView() {
    val mockUserData = UserData(
        gender = Gender.MALE,
        age = 25,
        weight = 70f,
        height = 180,
        activityLevel = ActivityLevel.HIGH,
        goalType = GoalType.LOSE_WEIGHT,
        carbRatio = 0.5f,
        proteinRatio = 0.3f,
        fatRatio = 0.2f,
        shouldShowOnboarding = false
    )

    CalorieTrackerTheme {
        TrackerOverViewScreen(
            onNavigateToSearch = { _, _, _, _ -> },
            userData = mockUserData,
            state = TrackerOverViewState(),
            revealedCardIds = listOf(),
            onEvent = {},
            onItemExpanded = {},
            onItemCollapsed = {},
        )
    }
}
