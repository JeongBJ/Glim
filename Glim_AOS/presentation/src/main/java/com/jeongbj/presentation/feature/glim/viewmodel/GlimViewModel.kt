package com.jeongbj.presentation.feature.glim.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.quote.usecase.QuoteUseCases
import com.jeongbj.presentation.common.paging.QuotePagingSource
import com.jeongbj.presentation.feature.glim.GlimAction
import com.jeongbj.presentation.feature.glim.GlimRoute
import com.jeongbj.presentation.feature.glim.GlimState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class GlimViewModel @Inject constructor(
    private val quoteUseCases: QuoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(GlimState())

    val state = _state.asStateFlow()
    private val quoteTrigger = MutableStateFlow<QuoteRequest>(QuoteRequest.List)

    val quotes = quoteTrigger
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
                            } else {
                                Timber.d("${result}: ")
                            }
                        }
                    }
                }
            }
        }
        .cachedIn(viewModelScope)


    fun onAction(action: GlimAction) {

    }


    init {
        val quoteSeq = savedStateHandle.toRoute<GlimRoute>().quoteSeq
        Timber.d("$quoteSeq: ")
        if (quoteSeq != null) getQuote(quoteSeq)
        else getQuotes()
    }


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
