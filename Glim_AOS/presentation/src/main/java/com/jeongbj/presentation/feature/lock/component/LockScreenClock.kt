package com.jeongbj.presentation.feature.lock.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.common.preview.Previews
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun LockScreenClock(
    modifier: Modifier = Modifier,
) {
    var now by remember {
        mutableStateOf(LocalDateTime.now())
    }

    LaunchedEffect(Unit) {
        while (true) {
            now = LocalDateTime.now()
            val delayMillis = (60 - now.second) * 1000L - now.nano / 1_000_000L
            delay(delayMillis.milliseconds)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = now.format(DateTimeFormatter.ofPattern("HH:mm")),
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = now.format(
                DateTimeFormatter.ofPattern("M월 d일 EEEE", Locale.KOREAN)
            ),
            color = Color.White
        )
    }
}

@Previews
@Composable
fun LockScreenClockPreview() {
    LockScreenClock()
}