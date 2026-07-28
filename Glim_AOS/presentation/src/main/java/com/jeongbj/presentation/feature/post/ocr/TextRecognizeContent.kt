package com.jeongbj.presentation.feature.post.ocr

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.ImageTransformState
import com.jeongbj.presentation.feature.post.component.TransformableImage
import com.jeongbj.presentation.theme.DarkThemeScreen

@Composable
fun TextRecognizeContent(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    onCompleteClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    var selectedRect by remember { mutableStateOf<Rect?>(null) }
    var recognizedText by remember { mutableStateOf("test") }
    var gestureMode by remember { mutableStateOf(GestureMode.RECT) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var viewportSize by remember { mutableStateOf(IntSize.Zero) }
    var transformState by remember { mutableStateOf(ImageTransformState()) }
    val recognizer = remember { MLKitTextRecognizer() }


    val context = LocalContext.current

    LaunchedEffect(selectedRect, imageUri) {
        imageUri ?: return@LaunchedEffect
        selectedRect ?: return@LaunchedEffect

        selectedBitmap = context.toBitmap(
            imageUri = imageUri,
            selectedRect = selectedRect!!,
            viewportSize = viewportSize,
            transformState = transformState
        )

        selectedBitmap?.let {
            recognizedText = recognizer.recognizeText(it)
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TextRecognizeHeaderSection(
                onCloseClicked = onCloseClicked,
                onCompleteClicked = { onCompleteClicked(recognizedText) }
            )

            if (selectedBitmap == null) {
                Box (
                    modifier = Modifier.weight(1f)
                ){
                    TransformableImage(
                        imageUri = imageUri,
                        onViewportChanged = { viewportSize = it },
                        onTransformChanged = { transformState = it }
                    )

                    if (gestureMode == GestureMode.RECT) {
                        SelectionOverlay(
                            selectionRect = selectedRect,
                            onSelectionChanged = { selectedRect = it }
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        bitmap = selectedBitmap!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = recognizedText,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

        }

        Surface(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .align(Alignment.CenterEnd),
            color = Color.DarkGray.copy(alpha = 0.6f),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                ActionButton(
                    onClick = { gestureMode = GestureMode.RECT },
                    painter = painterResource(R.drawable.ic_rect),
                    tint = if (gestureMode == GestureMode.RECT) Color.Yellow else Color.White
                )

                ActionButton(
                    onClick = { gestureMode = GestureMode.TRANSFORM },
                    painter = painterResource(R.drawable.ic_touch_app),
                    tint = if (gestureMode == GestureMode.TRANSFORM) Color.Yellow else Color.White
                )

                ActionButton(
                    onClick = {
                        selectedRect = null
                        selectedBitmap = null
                        transformState = ImageTransformState()
                        gestureMode = GestureMode.NONE
                    },
                    painter = painterResource(R.drawable.ic_undo)

                )

            }
        }


    }
}
@RequiresApi(Build.VERSION_CODES.P)
fun Context.toBitmap(
    imageUri: Uri,
    selectedRect: Rect,
    viewportSize: IntSize,
    transformState: ImageTransformState,
): Bitmap {
    val source = ImageDecoder.createSource(contentResolver, imageUri)
    val bitmap = ImageDecoder.decodeBitmap(source)

    val fitScale = minOf(
        viewportSize.width.toFloat() / bitmap.width,
        viewportSize.height.toFloat() / bitmap.height
    )
    val renderedWidth = bitmap.width * fitScale
    val renderedHeight = bitmap.height * fitScale

    val letterboxOffsetX = (viewportSize.width - renderedWidth) / 2f
    val letterboxOffsetY = (viewportSize.height - renderedHeight) / 2f

    val centerX = viewportSize.width / 2f
    val centerY = viewportSize.height / 2f
    val scale = transformState.scale
    val offsetX = transformState.offset.x
    val offsetY = transformState.offset.y

    fun inverseX(sx: Float) = (sx - offsetX - centerX) / scale + centerX
    fun inverseY(sy: Float) = (sy - offsetY - centerY) / scale + centerY

    val vpLeft   = inverseX(selectedRect.left)
    val vpTop    = inverseY(selectedRect.top)
    val vpRight  = inverseX(selectedRect.right)
    val vpBottom = inverseY(selectedRect.bottom)

    val bLeft   = ((vpLeft   - letterboxOffsetX) / fitScale).toInt()
    val bTop    = ((vpTop    - letterboxOffsetY) / fitScale).toInt()
    val bRight  = ((vpRight  - letterboxOffsetX) / fitScale).toInt()
    val bBottom = ((vpBottom - letterboxOffsetY) / fitScale).toInt()

    val left   = bLeft.coerceIn(0, bitmap.width - 1)
    val top    = bTop.coerceIn(0, bitmap.height - 1)
    val right  = bRight.coerceIn(left + 1, bitmap.width)
    val bottom = bBottom.coerceIn(top + 1, bitmap.height)

    return Bitmap.createBitmap(bitmap, left, top, right - left, bottom - top)
}
private enum class GestureMode {
    RECT,
    TRANSFORM,
    NONE

}

@Previews
@Composable
fun TextRecognizeContentPreview() {
    DarkThemeScreen {
        TextRecognizeContent(
            onCompleteClicked = { },
            onCloseClicked = { }
        )
    }
}