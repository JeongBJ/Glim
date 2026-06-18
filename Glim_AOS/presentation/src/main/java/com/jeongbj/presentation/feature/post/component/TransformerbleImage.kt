package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import coil3.compose.AsyncImage
import com.jeongbj.presentation.R

@Composable
fun TransformableImage(
    modifier: Modifier = Modifier,
    imageUri: Any? = null,
    imageAlpha: Float = 1f,
    enabled: Boolean = true,
    onViewportChanged: (IntSize) -> Unit = { },
    onTransformChanged: (ImageTransformState) -> Unit = { }
) {

    var viewportSize by remember { mutableStateOf(IntSize.Zero) }
    var imageTransformState by remember { mutableStateOf(ImageTransformState()) }


    if (imageUri == null) {
        Image(
            painter = painterResource(R.drawable.ic_image_empty),
            contentDescription = null,
            alpha = imageAlpha,
            modifier = modifier.fillMaxSize()
        )
        return
    }

    AsyncImage(
        model = imageUri,
        contentDescription = null,
        alpha = imageAlpha,
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = imageTransformState.scale
                scaleY = imageTransformState.scale

                translationX = imageTransformState.offset.x
                translationY = imageTransformState.offset.y
            }
            .onSizeChanged {
                viewportSize = it
                onViewportChanged(it)
            }
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput
                detectTransformGestures(panZoomLock = true) { centroid, pan, zoom, _ ->
                    imageTransformState = imageTransformState.transform(
                        centroid = centroid,
                        pan = pan,
                        zoom = zoom,
                        viewportSize = viewportSize
                    )
                    onTransformChanged(imageTransformState)
                }
            }
    )
}

data class ImageTransformState(
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero,
)

private fun ImageTransformState.transform(
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