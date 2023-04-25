package com.tydev.onboarding.presentation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.tydev.core.R
import com.tydev.core.ui.LocalSpacing
import com.tydev.core.ui.theme.CalorieTrackerTheme
import com.tydev.onboarding.presentation.components.ActionButton
import com.tydev.onboarding.presentation.components.GifImage

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GifImage(
                gif = R.drawable.on_boarding_image,
                modifier = Modifier.fillMaxWidth(WIDTH_RATIO),
            )
            Text(
                text = stringResource(id = R.string.welcome_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        ActionButton(
            text = stringResource(id = R.string.next),
            // 보통 presentation 로직을 viewModel 을 통해 구현해야하지만
            // 해당 기능만 존재하는 화면이므로 뷰모델 없이 구현(오버엔지니어링이라 생각)
            onClick = { onNextClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, name = "WelcomePreview")
@Composable
fun WelcomePreview() {
    CalorieTrackerTheme {
        WelcomeScreen(
            onNextClick = {}
        )
    }
}

private const val WIDTH_RATIO = 0.8f
