package com.jeongbj.presentation.feature.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.PostButtons
import com.jeongbj.presentation.feature.post.component.TransformableImage
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun PostContent(
    state: PostState,
    onAction: (PostAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TransformableImage(state, onAction)
        PostButtons(
            state = state,
            onAction = onAction
        )
    }
}

@Previews
@Composable
fun PostContentPreview() {
    GlimTheme {
        PostContent(
            state = PostState(),
        ) { }
    }
}