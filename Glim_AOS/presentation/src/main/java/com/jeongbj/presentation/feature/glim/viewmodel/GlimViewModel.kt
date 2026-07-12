package com.jeongbj.presentation.feature.glim.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jeongbj.android.image.ImageSaver
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.manager.TokenManager
import com.jeongbj.domain.quote.model.Quote
import com.jeongbj.domain.quote.usecase.QuoteUseCases
import com.jeongbj.presentation.common.paging.QuotePagingSource
import com.jeongbj.presentation.feature.glim.GlimAction
import com.jeongbj.presentation.feature.glim.GlimRoute
import com.jeongbj.presentation.feature.glim.GlimSideEffect
import com.jeongbj.presentation.feature.glim.GlimState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GlimViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
    private val imageSaver: ImageSaver,
    private val tokenManager: TokenManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(GlimState())

    val state = _state.asStateFlow()
    private val quoteTrigger = MutableStateFlow<QuoteRequest>(QuoteRequest.List)

    private val _sideEffect = MutableSharedFlow<GlimSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()


    private val localLikes = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val quotes = combine(
        quoteTrigger
            .flatMapLatest { request ->
                when (request) {
                    QuoteRequest.List -> {
                        Pager(
                            config = PagingConfig(pageSize = 5),
                            pagingSourceFactory = {
                                QuotePagingSource(getQuotesUseCase = quoteUseCases.getQuotesUseCase)
                            }
                        ).flow
                    }

                    is QuoteRequest.Single -> {
                        flow {
                            quoteUseCases.getQuoteUseCase(request.quoteSeq).collect { result ->
                                if (result is ResultType.Success) {
                                    emit(PagingData.from(listOf(result.data)))
                                }
                            }
                        }
                    }
                }
            }.cachedIn(viewModelScope),
        localLikes
    ) { pagingData, likes ->
        pagingData.map { quote ->
            val currentLiked = likes[quote.quoteSeq] ?: quote.liked
            val num = when {
                quote.liked && !currentLiked -> -1
                !quote.liked && currentLiked -> 1
                else -> 0
            }
            quote.copy(
                liked = currentLiked,
                numLikes = quote.numLikes + num
            )
        }
    }


    init {
        val quoteSeq = savedStateHandle.toRoute<GlimRoute>().quoteSeq
        if (quoteSeq != null) getQuote(quoteSeq)
        else getQuotes()
        getCurrentUserSeq()
    }

    private fun getCurrentUserSeq() {
        _state.update { it.copy(currentUserSeq = tokenManager.currentUserSeq() ?: 0) }
    }


    fun onAction(action: GlimAction) {
        when (action) {
            is GlimAction.OnBookInfoClicked -> onBookInfoClicked(action.isbn13)
            is GlimAction.OnLikeClicked -> onLikeClicked(action.quote)
            is GlimAction.OnShareClicked -> onShareClicked(action.quote)
            is GlimAction.OnSaveClicked -> onSaveClicked(action.imageUrl)
            is GlimAction.OnProfileClicked -> onProfileClicked(action.userSeq)
        }
    }

    private fun onProfileClicked(userSeq: Long) =
        _sideEffect.tryEmit(GlimSideEffect.NavigateToInfo(userSeq))

    private fun onShareClicked(quote: Quote) {
        _sideEffect.tryEmit(GlimSideEffect.ShareGlim(
            "[글:림]\n${quote.content}\nhttp://jeongbj.kro.kr/glim/share/${quote.quoteSeq}"
        ))
    }

    private fun onSaveClicked(imageUrl: String) {
        Timber.d("onSaveClicked: ${imageUrl}")
        viewModelScope.launch {
            imageSaver.saveImageToGallery(imageUrl).collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        _state.update { it.copy(isLoading = false) }
                        _sideEffect.emit(GlimSideEffect.ShowToast("저장이 완료되었습니다."))
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

    private fun onLikeClicked(quote: Quote) {
        viewModelScope.launch {
            quoteUseCases.likeQuoteUseCase(quote.quoteSeq).collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        localLikes.update { current ->
                            val currentLiked = current[quote.quoteSeq] ?: quote.liked
                            current + (quote.quoteSeq to !currentLiked)
                        }
                    }
                    is ResultType.Error -> { }
                    ResultType.Loading -> { }
                }
            }
        }
    }

    private fun onBookInfoClicked(isbn13: String) =
        _sideEffect.tryEmit(GlimSideEffect.NavigateToBookDetail(isbn13))


    private fun getQuote(quoteSeq: Long) {
        quoteTrigger.tryEmit(QuoteRequest.Single(quoteSeq))
    }

    private fun getQuotes() {
        quoteTrigger.tryEmit(QuoteRequest.List)
    }

    private sealed interface QuoteRequest {
        data object List : QuoteRequest
        data class Single(val quoteSeq: Long) : QuoteRequest
    }
}
