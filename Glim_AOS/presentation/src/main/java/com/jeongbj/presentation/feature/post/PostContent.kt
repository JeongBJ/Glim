package com.jeongbj.presentation.feature.post

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.jeongbj.android.extentions.toJpegByteArray
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.BookInfoSection
import com.jeongbj.presentation.feature.post.component.BookSearchBottomSheet
import com.jeongbj.presentation.feature.post.component.PostButtons
import com.jeongbj.presentation.feature.post.component.TextEditor
import com.jeongbj.presentation.feature.post.component.TextLayer
import com.jeongbj.presentation.feature.post.component.TransformableImage
import com.jeongbj.presentation.theme.GlimTheme
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch

@Composable
fun PostContent(
    state: PostState,
    onAction: (PostAction) -> Unit,
    onCapture: (ByteArray) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val graphicsLayer = rememberGraphicsLayer()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
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
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
        ) {
            TransformableImage(
                modifier = Modifier
                    .statusBarsPadding()
                    .clipToBounds(),
                imageUri = state.backgroundImageUri,
                imageAlpha = state.backgroundImageAlpha,
            )
            TextLayer(
                state = state,
                onAction = onAction,
                modifier = Modifier.padding(40.dp)
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            PostButtons(
                state = state,
                onAction = onAction,
                onCompleteClicked = {
                    scope.launch {
                        awaitFrame()
                        val bitmap = graphicsLayer.toImageBitmap()
                        val bytes = bitmap.asAndroidBitmap().toJpegByteArray()
                        onCapture(bytes)
                    }
                }
            )

            if (state.postText.isFocused) {
                TextEditor(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                        .align(Alignment.BottomCenter)
                )
            } else {
                BookInfoSection(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onAddBookInfoClicked = { onAction(PostAction.OnAddBookInfoClicked) },
                    book = state.selectedBook
                )
            }

            if(state.showBottomSheet) {
                BookSearchBottomSheet(
                    onDismiss = { onAction(PostAction.OnBookSelected(null)) },
                    onBookSelected = { onAction(PostAction.OnBookSelected(it)) }
                )
            }
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
            onCapture = { },
            onAction = { }
        )
    }
}