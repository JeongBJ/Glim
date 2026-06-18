package com.jeongbj.presentation.feature.post.ocr

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

@Composable
fun SelectionOverlay(
    modifier: Modifier = Modifier,
    selectionRect: Rect?,
    onSelectionChanged: (Rect) -> Unit
) {

    var start by remember { mutableStateOf<Offset?>(null) }
    var end by remember { mutableStateOf<Offset?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        start = offset
                        end = offset
                    },
                    onDrag = { change, _ ->
                        end = change.position
                    },
                    onDragEnd = {
                        createRect(start, end)?.let {
                            onSelectionChanged(it)
                        }
                    }
                )
            }
    ) {
        val rect = createRect(start, end)

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            rect?.let {
                drawRect(
                    color = Color.White,
                    topLeft = it.topLeft,
                    size = it.size,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }

}

private fun createRect(
    start: Offset?,
    end: Offset?
): Rect? {
    start?: return null
    end?: return null
    return Rect(
        left = min(start.x, end.x),
        top = min(start.y, end.y),
        right = max(start.x, end.x),
        bottom = max(start.y, end.y)
    )
}