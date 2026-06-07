package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.jeongbj.presentation.R
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState

@Composable
fun TransformableImage(
    state: PostState,
    onAction: (PostAction) -> Unit,
) {
    val transformableState = rememberTransformableState(
        onTransformation = { centroid, zoomChange, panChange, _ ->
            val oldScale = state.imageScale
            val newScale = (oldScale * zoomChange)
                .coerceIn(0.5f, 5f)

            val scaleFactor = newScale / oldScale

            val newOffset =
                (state.imageOffset + panChange) +
                        (centroid - state.imageOffset) * (1 - scaleFactor)

            onAction(
                PostAction.OnImageTransform(
                    scale = newScale,
                    offset = newOffset
                )
            )
        }
    )

    if(state.backgroundImageUri == null) {
        Image(
            painter = painterResource(R.drawable.ic_image_empty),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
                .graphicsLayer {
                    scaleX = state.imageScale
                    scaleY = state.imageScale

                    translationX = state.imageOffset.x
                    translationY = state.imageOffset.y
                }
                .transformable(transformableState)
        )
    } else

    AsyncImage(
        model = state.backgroundImageUri,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = state.imageScale
                scaleY = state.imageScale

                translationX = state.imageOffset.x
                translationY = state.imageOffset.y
            }
            .transformable(transformableState)
    )
}