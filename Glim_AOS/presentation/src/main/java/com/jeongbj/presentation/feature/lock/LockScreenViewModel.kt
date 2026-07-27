package com.jeongbj.presentation.feature.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.android.image.ImageSaver
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.usecase.QuoteUseCases
import com.jeongbj.presentation.common.paging.LockScreenPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LockScreenViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
    private val imageSaver: ImageSaver,
) : ViewModel() {
    private val _state = MutableStateFlow(LockScreenState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LockScreenSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    private val quoteTrigger = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1
    )
    val quotes = quoteTrigger.flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                LockScreenPagingSource(
                    getLockScreenQuotesUseCase = quoteUseCases.getLockScreenQuotesUseCase
                )
            }
        ).flow
    }.cachedIn(viewModelScope)

    init {
        quoteTrigger.tryEmit(Unit)
    }

    fun onAction(action: LockScreenAction) {
        when (action) {
            LockScreenAction.OnOpenCameraClicked -> _sideEffect.tryEmit(LockScreenSideEffect.OpenCamera)
            is LockScreenAction.OnOpenGlimClicked -> _sideEffect.tryEmit(LockScreenSideEffect.OpenQuote(action.quoteSeq))
            is LockScreenAction.OnSaveClicked -> onSaveClicked(action.imageUrl)
            LockScreenAction.OnUnlocked -> _sideEffect.tryEmit(LockScreenSideEffect.UnlockScreen)
        }
    }

    private fun onSaveClicked(imageUrl: String) {
        Timber.d("onSaveClicked: ${imageUrl}")
        viewModelScope.launch {
            imageSaver.saveImageToGallery(imageUrl).collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _sideEffect.emit(LockScreenSideEffect.ShowToast("저장이 완료되었습니다."))
                    }
                    is ResultType.Error -> {
                        Timber.d("onSaveClicked: ${result.exception}")
                        _state.update { it.copy(isLoading = false) }
                    }
                    ResultType.Loading -> _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}