package com.tydev.tracker.presentation.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.GoalType
import com.tydev.core.ui.LocalSpacing

@Composable
fun TopHeader(
    goalType: GoalType,
    activityLevel: ActivityLevel
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(spacing.spaceLarge)
    ) {
        Text(
            text = "You Can Do It🔥",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Row(
            Modifier.height(IntrinsicSize.Min)
        ) {
            Text(
                text = when (goalType) {
                    GoalType.GAIN_WEIGHT -> {
                        "Gain weight"
                    }
                    GoalType.KEEP_WEIGHT -> {
                        "Keep weight"
                    }
                    GoalType.LOSE_WEIGHT -> {
                        "Lose weight"
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Divider(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxHeight() // fill the max height
                    .width(2.dp)
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Text(
                text = when (activityLevel) {
                    ActivityLevel.LOW -> {
                        "Activity Low"
                    }
                    ActivityLevel.MEDIUM -> {
                        "Activity Medium"
                    }
                    ActivityLevel.HIGH -> {
                        "Activity High"
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
