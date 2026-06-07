package com.jeongbj.presentation.feature.post.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.jeongbj.domain.quote.usecase.QuoteUseCases
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostSideEffect
import com.jeongbj.presentation.feature.post.PostState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases
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
            PostAction.OnCreateTextClicked -> TODO()
            PostAction.OnImageGenerateClicked -> TODO()
            is PostAction.OnImageTransform -> {
                onImageTransform(action.offset, action.scale)
            }
            is PostAction.OnLaunchCameraClicked -> TODO()
            is PostAction.OnTextImageSelected -> TODO()
            PostAction.OnTextRecognitionClicked -> TODO()
            PostAction.ToggleButtonVisible -> TODO()
        }
    }

    private fun onImageTransform(offset: Offset, scale: Float) =
        _state.update {
            it.copy(
                imageScale = scale,
                imageOffset = offset
            )
        }


}