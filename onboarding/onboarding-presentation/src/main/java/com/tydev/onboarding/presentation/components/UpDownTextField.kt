package com.tydev.onboarding.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun UpDownTextField(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    upValue: () -> Unit,
    downValue: () -> Unit,
) {
    IconButton(onClick = upValue) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
        )
    }
    UnitTextField(
        value = value,
        onValueChange = onValueChange,
        unit = unit,
    )
    IconButton(onClick = downValue) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
        )
    }
}
