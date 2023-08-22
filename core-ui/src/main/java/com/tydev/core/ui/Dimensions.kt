package com.tydev.core.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// core 모듈 내부에 해당 데이터 클래스를 선언하게 되면 compose 의존성을 받을 수 없게 됨
// 이때 해결하는 방법은 core build.gradle 에 compose 의존성을 추가하는 것이 아닌
// 모듈을 하나 더 생성하는 것 core-ui
// core-ui 에 데이터 클래스를 생성함으로써 문제 해결
data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
)

val LocalSpacing = compositionLocalOf { Dimensions() }
