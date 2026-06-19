package com.jeongbj.presentation.feature.post.viewmodel

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.ResultType
import com.jeongbj.core.common.toMultipartImage
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.quote.model.CreateQuote
import com.jeongbj.domain.quote.usecase.QuoteUseCases
import com.jeongbj.presentation.common.camera.CameraTarget
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostSideEffect
import com.jeongbj.presentation.feature.post.PostState
import com.jeongbj.presentation.feature.post.PostText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PostSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: PostAction) {
        when (action) {
            PostAction.OnBackgroundImageClicked -> onBackgroundImageClicked()
            is PostAction.OnBackgroundImageSelected -> onBackgroundImageSelected(action.uri)
            PostAction.OnCloseClicked -> onCloseClicked()
            is PostAction.OnCreateTextClicked -> onCreateText()
            PostAction.OnImageGenerateClicked -> onImageGenerateClicked()
            is PostAction.OnLaunchCameraClicked -> onLaunchCameraClicked(action.cameraTarget)
            is PostAction.OnTextImageSelected -> onTextImageSelected(action.uri)
            PostAction.OnTextRecognitionClicked -> onTextRecognitionClicked()
            PostAction.ToggleButtonVisible -> toggleButtonVisible()
            is PostAction.OnTextDragged -> onTextDragged(action.offset)
            is PostAction.OnVerticalSliderValueChanged -> onVerticalSliderValueChanged(action.value)
            is PostAction.OnTextFocusChanged -> onTextFocusChanged(action.focus)
            is PostAction.OnTextChanged -> onTextChanged(action.text)
            is PostAction.OnFontFamilySelected -> onFontFamilySelected(action.fontFamily)
            is PostAction.OnTextColorSelected -> onTextColorSelected(action.color)
            PostAction.OnDecreaseFontSize -> onDecreaseFontSize()
            PostAction.OnIncreaseFontSize -> onIncreaseFontSize()
            PostAction.OnToggleBold -> onToggleBold()
            PostAction.OnToggleItalic -> onToggleItalic()
            is PostAction.OnBookSelected -> onBookSelected(action.book)
            PostAction.OnAddBookInfoClicked -> onAddBookInfoClicked()
            is PostAction.OnTextRecognized -> onTextRecognized(action.text)
        }
    }

    private fun onTextRecognized(text: String) =
        _state.update { it.copy(
            postText = it.postText.copy(
                text = text,
                isFocused = true
            ),
            ocrImageUri = null
        ) }

    private fun onAddBookInfoClicked() =
        _state.update { it.copy(showBottomSheet = true) }

    private fun onBookSelected(book: Book?) =
        _state.update { it.copy(
            showBottomSheet = false,
            selectedBook = book
        ) }


    fun onCaptured(bytes: ByteArray) {
        val content = state.value.postText.text
        if (content.isBlank()) {
            _sideEffect.tryEmit(PostSideEffect.ShowToast("텍스트를 입력해주세요"))
            return
        }

        val book = state.value.selectedBook ?: run {
            _sideEffect.tryEmit(PostSideEffect.ShowToast("책 정보를 입력해주세요"))
            return
        }

        viewModelScope.launch {
            quoteUseCases.saveQuoteUseCase(
                createQuote = CreateQuote(book.isbn13, content),
                image = bytes.toMultipartImage()
            ).collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        _state.update { it.copy(
                            isLoading = false,
                            uploadedQuote = result.data
                        ) }
                        _sideEffect.emit(PostSideEffect.ShowToast("글림이 성공적으로 업로드 되었습니다."))
                        _sideEffect.emit(PostSideEffect.NavigateBack)
                    }
                    ResultType.Loading -> { _state.update { it.copy(isLoading = true) } }
                    is ResultType.Error -> { _state.update { it.copy(isLoading = false) } }
                }
            }
        }
    }

    private fun onToggleItalic() {
        val currentStyle = state.value.postText.textStyleState
        _state.update { it.copy(postText = it.postText.copy(
            textStyleState = currentStyle.copy(
                isItalic = !currentStyle.isItalic
            )
        )) }
    }

    private fun onToggleBold() {
        val currentStyle = state.value.postText.textStyleState
        _state.update { it.copy(postText = it.postText.copy(
            textStyleState = currentStyle.copy(
                isBold = !currentStyle.isBold
            )
        )) }
    }

    private fun onIncreaseFontSize() {
        val currentStyle = state.value.postText.textStyleState
        if (currentStyle.fontSize >= 32f) return
        _state.update { it.copy(postText = it.postText.copy(
            textStyleState = currentStyle.copy(
                fontSize = currentStyle.fontSize + 2f
            )
        )) }
    }

    private fun onDecreaseFontSize() {
        val currentStyle = state.value.postText.textStyleState
        if (currentStyle.fontSize <= 12f) return
        _state.update { it.copy(postText = it.postText.copy(
            textStyleState = currentStyle.copy(
                fontSize = currentStyle.fontSize - 2f
            )
        )) }
    }


    private fun onTextColorSelected(color: Color) =
        _state.update { it.copy(postText = it.postText.copy(
            textStyleState = it.postText.textStyleState.copy(
                textColor = color
            )
        )) }

    private fun onFontFamilySelected(fontFamily: FontFamily) =
        _state.update { it.copy(postText = it.postText.copy(
            textStyleState = it.postText.textStyleState.copy(
                fontFamily = fontFamily
            )
        )) }

    private fun onImageGenerateClicked() {
        val content = _state.value.postText.text
        if (content.isBlank()) {
            _sideEffect.tryEmit(PostSideEffect.ShowToast("텍스트를 입력해주세요."))
            return
        }

        viewModelScope.launch {
            quoteUseCases.generateImageUseCase(content).collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        _state.update { it.copy(
                            backgroundImageUri = result.data,
                            isLoading = false
                        ) }
                    }

                    is ResultType.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        Timber.e(result.exception, "onImageGenerateClicked: ")
                    }
                    ResultType.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun onTextRecognitionClicked() =
        _sideEffect.tryEmit(PostSideEffect.OpenCamera(CameraTarget.OCR))

    private fun onTextImageSelected(uri: Uri?) =
        _state.update { it.copy(ocrImageUri = uri) }

    private fun onCloseClicked() =
        _sideEffect.tryEmit(PostSideEffect.ShowCloseDialog)

    private fun onBackgroundImageSelected(uri: Uri) =
        _state.update { it.copy(backgroundImageUri = uri) }

    private fun onBackgroundImageClicked() =
        _sideEffect.tryEmit(PostSideEffect.OpenGallery)

    private fun onTextChanged(text: String) =
        _state.update {
            it.copy(postText = it.postText.copy(
                    text = text
                )
            )
        }


    private fun onTextFocusChanged(focus: Boolean) =
        _state.update { it.copy(postText = it.postText.copy(
            isFocused = focus
        )) }

    private fun onLaunchCameraClicked(cameraTarget: CameraTarget) =
        _sideEffect.tryEmit(PostSideEffect.OpenCamera(cameraTarget))

    private fun onVerticalSliderValueChanged(value: Float) =
        _state.update { it.copy(backgroundImageAlpha = value) }


    private fun toggleButtonVisible() =
        _state.update { it.copy(buttonVisible = !it.buttonVisible) }

    private fun onCreateText() {
        if (state.value.postText.text.isNotEmpty()) return
        _state.update {
            it.copy(postText = PostText(
                isFocused = true
            ))
        }
    }

    private fun onTextDragged(offset: Offset) = _state.update {
        it.copy(postText = it.postText.copy(
            offset = it.postText.offset + offset
        ))
    }
}