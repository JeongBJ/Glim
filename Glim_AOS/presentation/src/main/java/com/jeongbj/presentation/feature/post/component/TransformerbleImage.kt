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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import coil3.compose.AsyncImage
import com.jeongbj.presentation.R
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState

@Composable
fun TransformableImage(
    state: PostState,
    onAction: (PostAction) -> Unit,
    modifier: Modifier = Modifier
) {

    var viewportSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    if(state.backgroundImageUri == null) {
        Image(
            painter = painterResource(R.drawable.ic_image_empty),
            contentDescription = null,
            alpha = state.backgroundImageAlpha,
            modifier = modifier.fillMaxSize()
                .graphicsLayer {
                    scaleX = state.imageTransform.scale
                    scaleY = state.imageTransform.scale

                    translationX = state.imageTransform.offset.x
                    translationY = state.imageTransform.offset.y
                }
                .onSizeChanged { viewportSize = it }
                .pointerInput(Unit) {
                    detectTransformGestures(panZoomLock = true) { centroid, pan, zoom, _ ->
                        onAction(PostAction.OnImageTransform(centroid, pan, zoom, viewportSize))
                    }
                }
        )
    }

    else {
        AsyncImage(
            model = state.backgroundImageUri,
            contentDescription = null,
            alpha = state.backgroundImageAlpha,
            modifier = modifier.fillMaxSize()
                .graphicsLayer {
                    scaleX = state.imageTransform.scale
                    scaleY = state.imageTransform.scale

                    translationX = state.imageTransform.offset.x
                    translationY = state.imageTransform.offset.y
                }
                .onSizeChanged { viewportSize = it }
                .pointerInput(Unit) {
                    detectTransformGestures(panZoomLock = true) { centroid, pan, zoom, _ ->
                        onAction(PostAction.OnImageTransform(centroid, pan, zoom, viewportSize))
                    }
                }
        )
    }
}