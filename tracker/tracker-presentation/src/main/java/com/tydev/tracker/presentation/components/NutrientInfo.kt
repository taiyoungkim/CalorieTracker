package com.tydev.tracker.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NutrientInfo(
    name: String,
    amount: Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 15.sp,
    amountColor: Color = MaterialTheme.colorScheme.onBackground,
    unitTextSize: TextUnit = 10.sp,
    unitColor: Color = MaterialTheme.colorScheme.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.labelSmall,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UnitDisplay(
            amount = amount,
            unit = unit,
            amountTextSize = amountTextSize,
            amountColor = amountColor,
            unitTextSize = unitTextSize,
            unitColor = unitColor,
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = nameTextStyle,
            fontWeight = FontWeight.Light,
        )
    }
}
