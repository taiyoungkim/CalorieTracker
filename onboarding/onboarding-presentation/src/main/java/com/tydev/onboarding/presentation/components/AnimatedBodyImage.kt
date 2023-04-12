package com.tydev.onboarding.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.times

@Composable
fun AnimatedHeightImage(
    number: Int,
    imageResId: Int,
    modifier: Modifier
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp - HEIGHT_INNER_MARGIN.dp

    val min = screenHeight/300

    val targetHeight = min(screenHeight, number * min)
    val animatedHeight by animateDpAsState(
        targetValue = targetHeight,
        label = "",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
        )
    )

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Height measurement image",
        modifier = modifier
            .height(animatedHeight),
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun AnimatedWeightImage(
    weight: Double,
    height: Int,
    imageResId: Int
) {
    val targetWidth = (weight * 2.5).dp
    val animatedWidth by animateDpAsState(
        targetValue = targetWidth,
        label = "",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
        )
    )

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Weight measurement image",
        modifier = Modifier
            .width(animatedWidth)
            .height(height.dp),
        contentScale = ContentScale.FillBounds
    )
}

const val HEIGHT_INNER_MARGIN = 150
