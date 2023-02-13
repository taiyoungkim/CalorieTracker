package com.tydev.tracker.presentation.overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tydev.core.R
import kotlin.math.absoluteValue

@Composable
fun NutrientsBar(
    value: Int,
    goal: Int,
    color: Color,
    name: String,
    modifier: Modifier = Modifier
) {
    val background = Color.LightGray
    val goalExceededColor = MaterialTheme.colorScheme.error
    val widthRatio = remember {
        Animatable(0f)
    }

    val data = if (value <= goal)
        stringResource(id = R.string.left)
    else
        stringResource(id = R.string.over)

    LaunchedEffect(key1 = value) {
        widthRatio.animateTo(
            targetValue = if (goal > 0) {
                value / goal.toFloat()
            } else 0f,
        )
    }
    Column {
        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Canvas(modifier = modifier) {
            if (value <= goal) {
                val valueWidth = widthRatio.value * size.width
                drawRoundRect(
                    color = background,
                    size = size,
                    cornerRadius = CornerRadius(100f)
                )
                drawRoundRect(
                    color = color,
                    size = Size(
                        width = valueWidth,
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(100f)
                )
            } else {
                drawRoundRect(
                    color = goalExceededColor,
                    size = size,
                    cornerRadius = CornerRadius(100f)
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "${(goal - value).absoluteValue}${stringResource(id = R.string.grams)} $data",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
