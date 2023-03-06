package com.tydev.onboarding.presentation.height

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tydev.core.R
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.util.UiEvent
import com.tydev.onboarding.presentation.components.ActionButton
import com.tydev.onboarding.presentation.components.RulerSlider
import com.tydev.onboarding.presentation.components.RulerSliderVertical

@Composable
fun HeightScreen(
    snackbarHostState: SnackbarHostState,
    onNextClick: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Text(
            text = stringResource(id = R.string.whats_your_height),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
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
                    viewModel.updateHeight(value.toString())
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
            onClick = viewModel::onNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
        )
    }
}
