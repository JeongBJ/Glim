package com.jeongbj.presentation.feature.post

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import com.jeongbj.presentation.common.camera.CameraTarget

data class PostState(
    val backgroundImageUri: Uri? = null,
    val ocrImageUri: Uri? = null,
    val buttonVisible: Boolean = true,
    val imageScale: Float = 1f,
    val imageOffset: Offset = Offset.Zero,
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
    data class OnImageTransform(val scale: Float, val offset: Offset): PostAction
}

sealed interface PostSideEffect {
    data class OpenCamera(val cameraTarget: CameraTarget): PostSideEffect
}