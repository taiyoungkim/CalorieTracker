package com.tydev.onboarding.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedHeightImage(
    number: Int,
    imageResId: Int,
    modifier: Modifier
) {
    val targetHeight = (number * 1.2).dp
    val animatedHeight by animateDpAsState(targetHeight, label = "")

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
    val animatedWidth by animateDpAsState(targetWidth, label = "")

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Weight measurement image",
        modifier = Modifier
            .width(animatedWidth)
            .height(height.dp),
        contentScale = ContentScale.FillBounds
    )
}
