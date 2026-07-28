package com.jeongbj.presentation.feature.post

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.presentation.common.camera.CameraTarget
import com.jeongbj.presentation.theme.glimDefaultFont

data class PostState(
    val backgroundImageUri: Any? = null,
    val backgroundImageAlpha: Float = 1f,
    val ocrImageUri: Uri? = null,
    val buttonVisible: Boolean = true,
    val postText: PostText = PostText(),
    val isLoading: Boolean = false,
    val isGenerating: Boolean = false,
    val selectedBook: Book? = null,
    val uploadedQuote: Quote? = null,
    val showBottomSheet: Boolean = false
)

sealed interface PostAction {
    data class OnBackgroundImageSelected(val uri: Uri): PostAction
    data class OnTextImageSelected(val uri: Uri?): PostAction
    data object OnCloseClicked: PostAction
    data object ToggleButtonVisible: PostAction
    data object OnImageGenerateClicked: PostAction
    data object OnTextRecognitionClicked: PostAction
    data object OnBackgroundImageClicked: PostAction
    data object OnCreateTextClicked: PostAction
    data class OnLaunchCameraClicked(val cameraTarget: CameraTarget): PostAction
    data class OnTextDragged(val offset: Offset): PostAction
    data class OnTextFocusChanged(val focus: Boolean): PostAction
    data class OnTextChanged(val text: String): PostAction
    data class OnVerticalSliderValueChanged(val value: Float): PostAction
    data class OnTextColorSelected(val color: Color): PostAction
    data class OnFontFamilySelected(val fontFamily: FontFamily): PostAction
    data object OnToggleBold: PostAction
    data object OnToggleItalic: PostAction
    data object OnIncreaseFontSize: PostAction
    data object OnDecreaseFontSize: PostAction
    data class OnBookSelected(val book: Book?): PostAction
    data object OnAddBookInfoClicked: PostAction
    data class OnTextRecognized(val text: String): PostAction
}

sealed interface PostSideEffect {
    data class OpenCamera(val cameraTarget: CameraTarget): PostSideEffect
    data object OpenGallery: PostSideEffect
    data object ShowCloseDialog: PostSideEffect
    data class ShowToast(val msg: String): PostSideEffect
    data object NavigateBack: PostSideEffect
}

data class PostText(
    val text: String = "",
    val offset: Offset = Offset.Zero,
    val isFocused: Boolean = false,
    val isDragging: Boolean = false,
    val textStyleState: TextStyleState = TextStyleState()
)

data class TextStyleState(
    val fontSize: Float = 16f,
    val textColor: Color = Color.White,
    val fontFamily: FontFamily = glimDefaultFont,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
) {
    val fontSizeUnit: TextUnit get() = fontSize.sp
    val fontWeight: FontWeight get() = if (isBold) FontWeight.Bold else FontWeight.Normal
    val fontStyle: FontStyle get() = if (isItalic) FontStyle.Italic else FontStyle.Normal
}
