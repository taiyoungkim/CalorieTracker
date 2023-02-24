package com.tydev.tracker.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.tydev.core.R
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.util.OnBottomReached
import com.tydev.core.ui.util.UiEvent
import com.tydev.tracker.domain.model.MealType
import com.tydev.tracker.presentation.search.components.SearchTextField
import com.tydev.tracker.presentation.search.components.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    snackbarHostState: SnackbarHostState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
                }
                is UiEvent.NavigateUp -> onNavigateUp()
                else -> Unit
            }
        }
    }

    listState.OnBottomReached {
        viewModel.onEvent(SearchEvent.OnSearch)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.add_meal, mealName),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spacing.spaceMedium)
            ) {
                Spacer(modifier = Modifier.padding(padding))
                SearchTextField(
                    text = state.query,
                    onValueChange = {
                        viewModel.onEvent(SearchEvent.OnQueryChange(it))
                    },
                    shouldShowHint = state.isHintVisible,
                    onSearch = {
                        keyboardController?.hide()
                        viewModel.onEvent(SearchEvent.OnSearch)
                    },
                    onFocusChanged = {
                        viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
                    }
                )

                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    items(state.trackableFood) { food ->
                        TrackableFoodItem(
                            trackableFoodUiState = food,
                            onClick = {
                                viewModel.onEvent(SearchEvent.OnToggleTrackableFood(food.food))
                            },
                            onAmountChange = {
                                viewModel.onEvent(
                                    SearchEvent.OnAmountForFoodChange(
                                        food.food, it
                                    )
                                )
                            },
                            onTrack = {
                                keyboardController?.hide()
                                viewModel.onEvent(
                                    SearchEvent.OnTrackFoodClick(
                                        food = food.food,
                                        mealType = MealType.fromString(mealName),
                                        date = LocalDate.of(year, month, dayOfMonth)
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isSearching -> CircularProgressIndicator()
                    state.trackableFood.isEmpty() -> {
                        Text(
                            text = stringResource(id = R.string.no_results),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}
