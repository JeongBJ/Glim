package com.jeongbj.presentation.feature.lock.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SlideToUnlockButton(
    onUnlocked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var trackWidthPx by remember { mutableFloatStateOf(0f) }
    val thumbSizePx = with(density) { 52.dp.toPx() }

    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val progress by remember {
        derivedStateOf {
            val maxOffset = trackWidthPx - thumbSizePx
            if (maxOffset <= 0f) 0f else (offsetX.value / maxOffset).coerceIn(0f, 1f)
        }
    }

    Box(
        modifier = modifier
            .widthIn(max = 280.dp)
            .fillMaxWidth()
            .height(56.dp)
            .onSizeChanged { trackWidthPx = it.width.toFloat() }
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White.copy(alpha = 0.15f)),
    ) {
        Text(
            text = "밀어서 잠금 해제",
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(1f - progress),
            color = Color.White
        )

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .size(52.dp)
                .clip(CircleShape)
                .background(Color.White)
                .pointerInput(trackWidthPx) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                val maxOffset = trackWidthPx - thumbSizePx
                                val newValue = (offsetX.value + dragAmount)
                                    .coerceIn(0f, maxOffset)
                                offsetX.snapTo(newValue)
                            }
                        },
                        onDragEnd = {
                            coroutineScope.launch {
                                if (progress >= 0.9f) {
                                    offsetX.animateTo(
                                        targetValue = trackWidthPx - thumbSizePx,
                                        animationSpec = tween(150)
                                    )
                                    onUnlocked()
                                } else {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                                }
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_forward),
                contentDescription = null,
            )
        }
    }
}