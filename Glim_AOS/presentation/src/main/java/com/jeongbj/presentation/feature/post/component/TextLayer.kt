package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.visible
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState
import kotlin.math.roundToInt

@Composable
fun TextLayer(
    state: PostState,
    onAction: (PostAction) -> Unit,
) {
    Text(
        text = state.postText.text,
        color = state.postText.color,
        fontSize = state.postText.fontSize.sp,
        modifier = Modifier
            .visible(state.postText.text.isNotBlank())
            .offset {
                IntOffset(
                    state.postText.offset.x.roundToInt(),
                    state.postText.offset.y.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    onAction(
                        PostAction.OnTextDragged(
                            dragAmount
                        )
                    )
                }
            }
    )
}