package com.tydev.tracker.presentation.overview.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tydev.core.R
import com.tydev.core.ui.CarbColor
import com.tydev.core.ui.FatColor
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.ProteinColor
import com.tydev.tracker.presentation.overview.TrackerOverViewState

@Composable
fun NutrientsHeader(
    state: TrackerOverViewState,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val animatedCalorieCount = animateIntAsState(
        targetValue = state.totalCalories
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.spaceLarge)
    ) {
        Row(
            modifier = Modifier.padding(spacing.spaceSmall)
        ) {
            NutrientsBarInfo(
                value = animatedCalorieCount.value,
                goal = state.caloriesGoal,
                name = stringResource(id = R.string.kcal),
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.width(spacing.spaceExtraLarge))
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NutrientsBar(
                    value = state.totalCarbs,
                    goal = state.carbsGoal,
                    color = CarbColor,
                    name = "CARB",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                NutrientsBar(
                    value = state.totalProtein,
                    goal = state.proteinGoal,
                    color = ProteinColor,
                    name = "PROTEIN",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                NutrientsBar(
                    value = state.totalFat,
                    goal = state.fatGoal,
                    color = FatColor,
                    name = "FAT",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        }
    }
}
