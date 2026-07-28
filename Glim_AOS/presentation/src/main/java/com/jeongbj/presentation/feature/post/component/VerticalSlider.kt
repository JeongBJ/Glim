package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalSlider(
    state: PostState,
    onAction: (PostAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Slider(
        value = state.backgroundImageAlpha,
        onValueChange = { onAction(PostAction.OnVerticalSliderValueChanged(it)) },
        valueRange = 0.1f..1f,
        thumb = {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        Color.White,
                        CircleShape
                    )
            )
        },
        track = { sliderState ->
            SliderDefaults.Track(
                sliderState = sliderState,
                modifier = Modifier.height(4.dp),
                thumbTrackGapSize = 0.dp,
                trackInsideCornerSize = 0.dp,
                drawStopIndicator = null,
                colors = colors().copy(activeTrackColor = Color.White, inactiveTrackColor = Color.Gray)
            )
        },
        modifier = modifier
            .fillMaxHeight()
            .graphicsLayer {
                rotationZ = -90f
                transformOrigin = TransformOrigin(0f, 0f)
            }.layout { measurable, constraints ->
                val placeable = measurable.measure(
                    Constraints(
                        minWidth = constraints.minHeight,
                        maxWidth = constraints.maxHeight,
                        minHeight = constraints.minWidth,
                        maxHeight = constraints.maxHeight,
                    )
                )
                layout(placeable.height, placeable.width) {
                    placeable.place(-placeable.width, 0)
                }
            }
            .then(modifier)
    )
}