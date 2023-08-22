package com.tydev.tracker.presentation.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
                Text(
                    text = "delete",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            modifier = Modifier.background(Color.Red),
        )
        IconButton(
            onClick = onEdit,
            content = {
                Text(
                    text = "modify",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            modifier = Modifier.background(Color.Unspecified),
        )
    }
}

@Preview
@Composable
fun ActionsRowPreview() {
    ActionsRow(
        onDelete = {},
        onEdit = {},
    )
}
