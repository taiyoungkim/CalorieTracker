package com.tydev.tracker.presentation.overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tydev.core.R
import com.tydev.tracker.presentation.components.UnitDisplay

@Composable
fun NutrientsBarInfo(
    value: Int,
    goal: Int,
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val background = MaterialTheme.colorScheme.background
    val goalExceededColor = MaterialTheme.colorScheme.error
    val angelRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = value) {
        angelRatio.animateTo(
            targetValue = if (goal > 0) {
                value / goal.toFloat()
            } else 0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        ) {
            drawArc(
                color = if (value <= goal) background else goalExceededColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UnitDisplay(
                amount = value,
                unit = stringResource(id = R.string.grams),
                amountColor = if (value <= goal) {
                    MaterialTheme.colorScheme.onPrimary
                } else goalExceededColor,
            )
            Text(
                text = name,
                color = if(value <= goal) {
                    MaterialTheme.colorScheme.onPrimary
                } else goalExceededColor,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Light
            )
        }
    }
}