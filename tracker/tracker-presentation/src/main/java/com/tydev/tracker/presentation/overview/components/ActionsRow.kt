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
import androidx.compose.ui.unit.dp

@Composable
fun ActionsRow(
    onDelete: () -> Unit,
    onEdit: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
//            modifier = Modifier.size(actionIconSize),
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
//            modifier = Modifier.size(actionIconSize),
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
