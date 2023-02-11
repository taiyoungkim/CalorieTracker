package com.tydev.tracker.presentation.overview.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.tydev.core.R
import com.tydev.core.ui.LocalSpacing
import com.tydev.tracker.presentation.components.NutrientInfo
import com.tydev.tracker.presentation.components.UnitDisplay
import com.tydev.tracker.presentation.overview.Meal

@Composable
fun ExpandableMeal(
    meal: Meal,
    onToggleClick: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleClick() }
                .padding(spacing.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = meal.drawableRes),
                colorFilter = if (meal.calories > 0) ColorFilter.tint(color = MaterialTheme.colorScheme.primary) else null,
                contentDescription = meal.name.asString(context)
            )
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = meal.name.asString(context),
                    style = MaterialTheme.typography.titleSmall
                )
                UnitDisplay(
                    amount = meal.calories,
                    unit = stringResource(id = R.string.kcal),
                    amountTextSize = 15.sp
                )
            }
            Row {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = meal.carbs,
                    unit = stringResource(id = R.string.grams)
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = meal.protein,
                    unit = stringResource(id = R.string.grams)
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = meal.fat,
                    unit = stringResource(id = R.string.grams)
                )
            }
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Icon(
                imageVector = if (meal.isExpanded) {
                    Icons.Default.KeyboardArrowUp
                } else Icons.Default.KeyboardArrowDown,
                contentDescription = if (meal.isExpanded) {
                    stringResource(id = R.string.collapse)
                } else stringResource(id = R.string.extend)
            )
        }
        AnimatedVisibility(visible = meal.isExpanded) {
            content()
        }
    }
}
