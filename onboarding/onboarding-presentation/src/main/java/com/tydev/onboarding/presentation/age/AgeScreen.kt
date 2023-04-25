package com.tydev.onboarding.presentation.age

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tydev.core.R
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.theme.CalorieTrackerTheme
import com.tydev.core.ui.util.UiEvent
import com.tydev.onboarding.presentation.components.ActionButton
import com.tydev.onboarding.presentation.components.UpDownTextField

@Composable
internal fun AgeRoute(
    onNextClick: () -> Unit,
    viewModel: AgeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
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

    val age = remember(viewModel.age) { mutableStateOf(viewModel.age) }

    AgeScreen(
        age = age,
        onAgeEnter = viewModel::onAgeEnter,
        plusAge = viewModel::plusAge,
        minusAge = viewModel::minusAge,
        onNextClick = viewModel::onNextClick,
    )
}

@Composable
internal fun AgeScreen(
    age: MutableState<Int>,
    onAgeEnter: (String) -> Unit,
    plusAge: () -> Unit,
    minusAge: () -> Unit,
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            UpDownTextField(
                value = age.value.toString(),
                onValueChange = onAgeEnter,
                unit = stringResource(id = R.string.years),
                upValue = plusAge,
                downValue = minusAge
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

@Preview(showBackground = true, name = "AgeScreen Preview")
@Composable
fun AgeScreenPreview() {
    val age = remember { mutableStateOf(DEFAULT_AGE) }

    CalorieTrackerTheme {
        AgeScreen(
            age = age,
            onAgeEnter = {},
            plusAge = {},
            minusAge = {},
            onNextClick = {},
        )
    }
}
