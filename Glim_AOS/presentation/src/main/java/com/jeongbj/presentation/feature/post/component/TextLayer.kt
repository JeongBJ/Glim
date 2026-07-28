package com.jeongbj.presentation.feature.post.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState
import kotlin.math.roundToInt

@Composable
fun TextLayer(
    state: PostState,
    onAction: (PostAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val postText = state.postText
    val textStyle = postText.textStyleState

    if (postText.text.isEmpty() && !postText.isFocused) return

    val textFieldState = rememberTextFieldState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    BackHandler(postText.isFocused) {
        focusRequester.freeFocus()
        onAction(PostAction.OnTextFocusChanged(false))
        focusManager.clearFocus()
    }

    LaunchedEffect(postText.isFocused) {
        if(postText.isFocused) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(state.postText.text) {
        if (textFieldState.text.toString() != state.postText.text) {
            textFieldState.setTextAndPlaceCursorAtEnd(
                state.postText.text
            )
        }
    }

    LaunchedEffect(textFieldState) {
        snapshotFlow {
            textFieldState.text.toString()
        }.collect { text ->
            onAction(PostAction.OnTextChanged(text))
        }
    }

    Box(
        modifier = modifier
            .offset {
                IntOffset(
                    state.postText.offset.x.roundToInt(),
                    state.postText.offset.y.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    onAction(PostAction.OnTextDragged(dragAmount))
                }
            }
    ) {
        BasicTextField(
            state = textFieldState,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                textAlign = TextAlign.Center,
                lineHeight = textStyle.fontSizeUnit * 2f,
                fontSize = textStyle.fontSizeUnit,
                fontWeight = textStyle.fontWeight,
                fontFamily = textStyle.fontFamily,
                color = textStyle.textColor,
                fontStyle = textStyle.fontStyle
            ),
            readOnly = postText.isDragging,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .padding(32.dp)
                .minimumInteractiveComponentSize()
                .focusRequester(focusRequester)
                .onFocusChanged{ focusState ->
                    onAction(PostAction.OnTextFocusChanged(focusState.isFocused))
                }
        )
    }
}