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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tydev.onboarding.presentation.R
import com.tydev.core.domain.model.Gender
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.util.UiEvent
import com.tydev.onboarding.presentation.components.ActionButton
import com.tydev.onboarding.presentation.components.SelectableImageButton

@Composable
fun GenderScreen(
    onNextClick: () -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
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
                    modifier = Modifier.weight(1f).defaultMinSize(minHeight = 350.dp),
                    text = stringResource(id = com.tydev.core.R.string.male),
                    isSelected = viewModel.selectedGender == Gender.MALE,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGenderClick(Gender.MALE)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    imageResId = if (viewModel.selectedGender == Gender.MALE) R.drawable.ic_man_select else R.drawable.ic_man_unselect
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                SelectableImageButton(
                    modifier = Modifier.weight(1f).defaultMinSize(minHeight = 350.dp),
                    text = stringResource(id = com.tydev.core.R.string.female),
                    isSelected = viewModel.selectedGender == Gender.FEMALE,
                    color = MaterialTheme.colorScheme.primary,
                    selectedTextColor = Color.White,
                    onClick = {
                        viewModel.onGenderClick(Gender.FEMALE)
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    imageResId = if (viewModel.selectedGender == Gender.FEMALE) R.drawable.ic_woman_select else R.drawable.ic_woman_unselect
                )
            }
        }

        ActionButton(
            text = stringResource(id = com.tydev.core.R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
        )
    }
}
