package com.jeongbj.presentation.feature.lock.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LockScreenUnlock(
    onUnlocked: () -> Unit,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val screenHeightPx = with(density) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    val unlockThresholdPx = screenHeightPx * 0.35f // 35% 이상 밀면 잠금 해제

    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val progress by remember {
        derivedStateOf {
            (-offsetY.value / unlockThresholdPx).coerceIn(0f, 1f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        coroutineScope.launch {
                            val newValue = (offsetY.value + dragAmount)
                                .coerceIn(-screenHeightPx, 0f)
                            offsetY.snapTo(newValue)
                        }
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            if (progress >= 0.9f) {
                                offsetY.animateTo(
                                    targetValue = -screenHeightPx,
                                    animationSpec = tween(250)
                                )
                                onUnlocked()
                            } else {
                                offsetY.animateTo(
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
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = offsetY.value
                    alpha = 1f - progress          // 드래그할수록 투명해짐
                    val scale = 1f - progress * 0.15f
                    scaleX = scale
                    scaleY = scale
                }
        ) {
            content()
        }
    }
}