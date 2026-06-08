package com.jeongbj.presentation.feature.post.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import com.jeongbj.domain.quote.usecase.QuoteUseCases
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PostSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: PostAction) {
        when (action) {
            PostAction.OnBackgroundImageClicked -> TODO()
            is PostAction.OnBackgroundImageSelected -> TODO()
            PostAction.OnCloseClicked -> TODO()
            PostAction.OnCompleteClicked -> TODO()
            is PostAction.OnCreateTextClicked -> {
                onCreateText()
            }
            PostAction.OnImageGenerateClicked -> TODO()
            is PostAction.OnImageTransform -> {
                onImageTransform(action.centroid, action.pan, action.zoom, action.viewportSize)
            }
            is PostAction.OnLaunchCameraClicked -> TODO()
            is PostAction.OnTextImageSelected -> TODO()
            PostAction.OnTextRecognitionClicked -> TODO()
            PostAction.ToggleButtonVisible -> {
                toggleButtonVisible()
            }
            is PostAction.OnTextDragged -> {
                onTextDragged(action.offset)
            }
            is PostAction.OnViewportSizeChanged -> {
                Timber.d("onAction: viewport ${action.size}")
                onViewportSizeChanged(action.size)
            }

            is PostAction.OnVerticalSliderValueChanged -> {
                onVerticalSliderValueChanged(action.value)
            }
        }
    }

    private fun onVerticalSliderValueChanged(value: Float) =
        _state.update { it.copy(backgroundImageAlpha = value) }


    private fun toggleButtonVisible() =
        _state.update { it.copy(buttonVisible = !it.buttonVisible) }

    private fun onViewportSizeChanged(size: IntSize) =
        _state.update { it.copy(viewportSize = size) }

    private fun onCreateText() {
        val center = Offset(
            state.value.viewportSize.width / 2f,
            state.value.viewportSize.height / 2f
        )
        _state.update {
            it.copy(postText = PostText(offset = center))
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