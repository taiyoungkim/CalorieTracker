package com.tydev.onboarding.presentation.gender

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tydev.onboarding.presentation.R
import com.tydev.core.domain.model.Gender
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.theme.CalorieTrackerTheme
import com.tydev.core.ui.util.UiEvent
import com.tydev.onboarding.presentation.components.ActionButton
import com.tydev.onboarding.presentation.components.SelectableImageButton

@Composable
internal fun GenderRoute(
    onNextClick: (Gender) -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick(viewModel.selectedGender)
                else -> Unit
            }
        }
    }

    GenderScreen(
        selectedGender = viewModel.selectedGender,
        onGenderClick = { viewModel.onGenderClick(it) },
        onNextClick = viewModel::onNextClick
    )
}

@Composable
fun GenderScreen(
    selectedGender: Gender,
    onGenderClick: (Gender) -> Unit,
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
                text = stringResource(id = com.tydev.core.R.string.whats_your_gender),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row {
                SelectableImageButton(
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 350.dp),
                    text = stringResource(id = com.tydev.core.R.string.male),
                    isSelected = selectedGender == Gender.MALE,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        onGenderClick(Gender.MALE)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    imageResId = if (selectedGender == Gender.MALE) R.drawable.ic_man_select else R.drawable.ic_man_unselect
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                SelectableImageButton(
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 350.dp),
                    text = stringResource(id = com.tydev.core.R.string.female),
                    isSelected = selectedGender == Gender.FEMALE,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        onGenderClick(Gender.FEMALE)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    imageResId = if (selectedGender == Gender.FEMALE) R.drawable.ic_woman_select else R.drawable.ic_woman_unselect
                )
            }
        }

        ActionButton(
            text = stringResource(id = com.tydev.core.R.string.next),
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, name = "GenderScreen Preview")
@Composable
fun GenderScreenPreview() {
    CalorieTrackerTheme {
        GenderScreen(
            selectedGender = Gender.MALE,
            onGenderClick = {},
            onNextClick = {},
        )
    }
}
