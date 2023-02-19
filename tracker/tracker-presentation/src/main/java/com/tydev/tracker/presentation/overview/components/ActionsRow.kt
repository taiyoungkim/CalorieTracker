package com.tydev.tracker.presentation.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tydev.core.ui.LocalSpacing

@Composable
fun ActionsRow(
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.spaceMedium, vertical = spacing.spaceSmall),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            onClick = onDelete,
            content = {
                Icon(
                    imageVector = Icons.Default.Close,
                    tint = Color.Gray,
                    contentDescription = "delete",
                )
            }
        )
        IconButton(
            onClick = onEdit,
            content = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = Color.Gray,
                    contentDescription = "edit",
                )
            },
        )
    }
}
