package com.jeongbj.presentation.feature.post

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import com.jeongbj.presentation.common.camera.CameraTarget

data class PostState(
    val backgroundImageUri: Uri? = null,
    val backgroundImageAlpha: Float = 1f,
    val ocrImageUri: Uri? = null,
    val buttonVisible: Boolean = true,
    val imageTransform: ImageTransformState = ImageTransformState(),
    val postText: PostText = PostText(),
    val viewportSize: IntSize = IntSize.Zero,
)

sealed interface PostAction {
    data class OnBackgroundImageSelected(val uri: Uri): PostAction
    data class OnTextImageSelected(val uri: Uri): PostAction
    data object OnCloseClicked: PostAction
    data object ToggleButtonVisible: PostAction
    data object OnCompleteClicked: PostAction
    data object OnImageGenerateClicked: PostAction
    data object OnTextRecognitionClicked: PostAction
    data object OnBackgroundImageClicked: PostAction
    data object OnCreateTextClicked: PostAction
    data class OnLaunchCameraClicked(val cameraTarget: CameraTarget): PostAction
    data class OnImageTransform(val centroid: Offset, val pan: Offset, val zoom: Float, val viewportSize: IntSize): PostAction
    data class OnTextDragged(val offset: Offset): PostAction
    data class OnViewportSizeChanged(val size: IntSize): PostAction
    data class OnVerticalSliderValueChanged(val value: Float): PostAction
}

sealed interface PostSideEffect {
    data class OpenCamera(val cameraTarget: CameraTarget): PostSideEffect
}

data class ImageTransformState(
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero,
)

data class PostText(
    val text: String = "",
    val offset: Offset = Offset.Zero,
    val fontSize: Float = 24f,
    val color: Color = Color.White,
)
