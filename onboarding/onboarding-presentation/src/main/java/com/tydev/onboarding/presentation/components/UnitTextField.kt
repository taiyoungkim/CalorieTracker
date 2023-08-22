package com.tydev.onboarding.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tydev.core.ui.LocalSpacing

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UnitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.primary,
        fontSize = UNIT_TEXT_FIELD_FONT_SIZE.sp,
    ),
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedContent(
            targetState = value,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically { -it } with slideOutVertically { it }
                } else {
                    slideInVertically { it } with slideOutVertically { -it }
                }
            },
        ) {
            BasicTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.isEmpty()) {
                        onValueChange("0")
                    } else {
                        onValueChange(newValue)
                    }
                },
                textStyle = textStyle,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .alignBy(LastBaseline),
            )
        }
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Text(
            text = unit,
            modifier = Modifier.alignBy(LastBaseline),
        )
    }
}

@Preview
@Composable
fun UnitTextFieldPreview() {
    UnitTextField(
        value = "20",
        onValueChange = {},
        unit = "unit",
    )
}

const val UNIT_TEXT_FIELD_FONT_SIZE = 70
