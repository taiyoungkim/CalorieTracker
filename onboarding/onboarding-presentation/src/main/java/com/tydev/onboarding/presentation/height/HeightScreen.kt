package com.tydev.onboarding.presentation.height

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tydev.core.R
import com.tydev.core.domain.model.Gender
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.theme.CalorieTrackerTheme
import com.tydev.core.ui.util.UiEvent
import com.tydev.onboarding.presentation.components.ActionButton
import com.tydev.onboarding.presentation.components.AnimatedHeightImage
import com.tydev.onboarding.presentation.components.RulerSliderVertical

@Composable
internal fun HeightRoute(
    gender: String,
    snackbarHostState: SnackbarHostState,
    onNextClick: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    HeightScreen(
        gender = gender,
        height = viewModel.height.toInt(),
        updateHeight = viewModel::updateHeight,
        onNextClick = viewModel::onNextClick
    )
}

@Composable
internal fun HeightScreen(
    gender: String,
    height: Int,
    updateHeight: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val resId = if (gender.equals(Gender.MALE.name))
        com.tydev.onboarding.presentation.R.drawable.ic_man_standing
    else
        com.tydev.onboarding.presentation.R.drawable.ic_woman_standing

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Text(
            text = stringResource(id = R.string.whats_your_height),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(bottom = 10.dp)
                ) {
                    AnimatedHeightImage(
                        number = height,
                        imageResId = resId,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    RulerSliderVertical(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 16.dp),
                        currentValueLabel = { value ->
                            Text(
                                text = "${(value / 1)}${stringResource(id = R.string.cm)}",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            updateHeight(value.toString())
                        },
                        indicatorLabel = { value ->
                            if (value % DIVIDER == 0) {
                                Text(
                                    text = "${(value / 1)}",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    )
                }
            }
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, name = "HeightScreen Preview")
@Composable
fun HeightScreenPreview() {
    CalorieTrackerTheme {
        HeightScreen(
            gender = "MALE",
            height = 180,
            updateHeight = {},
            onNextClick = {},
        )
    }
}

private const val DIVIDER = 5
