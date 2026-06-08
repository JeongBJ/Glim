package com.jeongbj.presentation.feature.post

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.PostButtons
import com.jeongbj.presentation.feature.post.component.TextLayer
import com.jeongbj.presentation.feature.post.component.TransformableImage
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun PostContent(
    state: PostState,
    onAction: (PostAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
              },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(4f / 7f)
        ) {
            TransformableImage(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .statusBarsPadding()
                    .clipToBounds()
            )
            TextLayer(
                state = state,
                onAction = onAction,
                modifier = Modifier.padding(40.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
                .onSizeChanged { size ->
                    onAction(
                        PostAction.OnViewportSizeChanged(size)
                    )
                }
        ) {
            PostButtons(
                state = state,
                onAction = onAction
            )
        }
    }
}

@Previews
@Composable
fun PostContentPreview() {
    GlimTheme {
        PostContent(
            state = PostState(
                postText = PostText(text = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest")
            ),
        ) { }
    }
}