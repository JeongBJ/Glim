package com.jeongbj.presentation.common.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.jeongbj.presentation.feature.post.ImageTransformState

fun ImageTransformState.transform(
    centroid: Offset,
    pan: Offset,
    zoom: Float,
    viewportSize: IntSize,
): ImageTransformState {
    val newScale = (scale * zoom).coerceIn(1f, 5f)
    val scaleFactor = newScale / scale
    var newOffset = offset + pan + (centroid - offset) * (1f - scaleFactor)

    val maxX = viewportSize.width * (newScale - 1f) / 2f

    val maxY = viewportSize.height * (newScale - 1f) / 2f

    newOffset = Offset(
        x = newOffset.x.coerceIn(-maxX, maxX),
        y = newOffset.y.coerceIn(-maxY, maxY),
    )

    return copy(
        scale = newScale,
        offset = newOffset,
    )
}