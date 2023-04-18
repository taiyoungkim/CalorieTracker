package com.tydev.onboarding.presentation.weight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.tydev.onboarding.presentation.components.AnimatedWeightImage
import com.tydev.onboarding.presentation.components.RulerSlider
import com.tydev.onboarding.presentation.components.rememberRulerSliderState

@Composable
internal fun WeightRoute(
    gender: String,
    snackbarHostState: SnackbarHostState,
    onNextClick: () -> Unit,
    viewModel: WeightViewModel = hiltViewModel()
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

    WeightScreen(
        gender = gender,
        weight = viewModel.weight.toDouble(),
        updateWeight = { viewModel.updateWeight(it.toString()) },
        onNextClick = viewModel::onNextClick
    )
}
@Composable
internal fun WeightScreen(
    gender: String,
    weight: Double,
    updateWeight: (Int) -> Unit,
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val resId = if (gender.equals(Gender.MALE.name))
        com.tydev.onboarding.presentation.R.drawable.ic_man_standing
    else
        com.tydev.onboarding.presentation.R.drawable.ic_woman_standing

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            AnimatedWeightImage(
                weight = weight,
                height = 200,
                imageResId = resId)

            RulerSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                state = rememberRulerSliderState(
                    currentValue = 60f,
                    range = 30..150,
                ),
                currentValueLabel = { value ->
                    Text(
                        text = "${(value / 1)}${stringResource(id = R.string.kg)}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    updateWeight(value)
                },
                indicatorLabel = { value ->
                    if (value % 5 == 0) {
                        Text(
                            text = "${(value / 1)}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, name = "WeightScreen Preview")
@Composable
fun WeightScreenPreview() {
    CalorieTrackerTheme {
        WeightScreen(
            gender = "MALE",
            weight = 60.0,
            updateWeight = {},
            onNextClick = {},
        )
    }
}
