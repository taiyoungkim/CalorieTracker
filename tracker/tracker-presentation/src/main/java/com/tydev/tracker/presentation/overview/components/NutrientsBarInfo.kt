package com.tydev.tracker.presentation.overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tydev.core.R
import kotlin.math.absoluteValue

@Suppress("MagicNumber")
@Composable
fun NutrientsBarInfo(
    value: Int,
    goal: Int,
    name: String,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val background = Color.LightGray
    val primaryColor = MaterialTheme.colorScheme.primary
    val goalExceededColor = MaterialTheme.colorScheme.error
    val angelRatio = remember {
        Animatable(0f)
    }

    val data = if (value <= goal)
        stringResource(id = R.string.left)
    else
        stringResource(id = R.string.over)

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
                .fillMaxWidth()
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
            if (value <= goal) {
                drawArc(
                    color = primaryColor,
                    startAngle = 270f,
                    sweepAngle = 360f * angelRatio.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (goal - value).absoluteValue.toString(),
                color = if (value <= goal) {
                    primaryColor
                } else goalExceededColor,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 40.sp,
                modifier = Modifier
            )
            Text(
                text = "$name $data",
                color = if (value <= goal) {
                    background
                } else goalExceededColor,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
