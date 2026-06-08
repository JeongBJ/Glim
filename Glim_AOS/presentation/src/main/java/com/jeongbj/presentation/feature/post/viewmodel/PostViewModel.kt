package com.jeongbj.presentation.feature.post.viewmodel

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.usecase.QuoteUseCases
import com.jeongbj.presentation.common.camera.CameraTarget
import com.jeongbj.presentation.common.util.transform
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
            PostAction.OnCompleteClicked -> TODO()
            is PostAction.OnCreateTextClicked -> onCreateText()
            PostAction.OnImageGenerateClicked -> onImageGenerateClicked()
            is PostAction.OnImageTransform -> onImageTransform(action.centroid, action.pan, action.zoom, action.viewportSize)
            is PostAction.OnLaunchCameraClicked -> onLaunchCameraClicked(action.cameraTarget)
            is PostAction.OnTextImageSelected -> onTextImageSelected(action.uri)
            PostAction.OnTextRecognitionClicked -> onTextRecognitionClicked()
            PostAction.ToggleButtonVisible -> toggleButtonVisible()
            is PostAction.OnTextDragged -> onTextDragged(action.offset)
            is PostAction.OnVerticalSliderValueChanged -> onVerticalSliderValueChanged(action.value)
            is PostAction.OnTextFocusChanged -> onTextFocusChanged(action.focus)
            is PostAction.OnTextChanged -> onTextChanged(action.text)

        }
    }

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
                        Timber.e(result.exception, "onImageGenerateClicked: ")
                    }
                    ResultType.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun onTextRecognitionClicked() =
        _sideEffect.tryEmit(PostSideEffect.OpenCamera(CameraTarget.OCR))

    private fun onTextImageSelected(uri: Uri) =
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


    private fun onImageTransform(centroid: Offset, pan: Offset, zoom: Float, viewportSize: IntSize) =
        _state.update { state ->
            state.copy(
                imageTransform = state.imageTransform.transform(
                    centroid = centroid,
                    pan = pan,
                    zoom = zoom,
                    viewportSize = viewportSize
                )
            )
        }


}