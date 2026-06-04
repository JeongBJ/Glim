package com.jeongbj.presentation.feature.book.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.book.usecase.BookUseCases
import com.jeongbj.presentation.feature.book.detail.BookDetailAction
import com.jeongbj.presentation.feature.book.detail.BookDetailRoute
import com.jeongbj.presentation.feature.book.detail.BookDetailSideEffect
import com.jeongbj.presentation.feature.book.detail.BookDetailState
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
class BookDetailViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<BookDetailSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        val isbn13 = savedStateHandle.toRoute<BookDetailRoute>().isbn13
        getBookDetail(isbn13)
    }

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnBackClick -> {
                navigateBack()
            }
            is BookDetailAction.OnBuyBookClick -> {
                onBuyBookClick(action.linkUrl)
            }
            is BookDetailAction.OnClickQuote -> TODO()
            is BookDetailAction.OnRegisterQuoteClick -> TODO()
            BookDetailAction.ToggleBookDescriptionExpanded -> {
                toggleBookDescriptionExpanded()
            }
        }
    }

    private fun getBookDetail(isbn13: String) {
        viewModelScope.launch {
            bookUseCases.searchBookByIsbn13(isbn13).collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        val data = result.data
                        Timber.d("getBookDetail: $data")
                        _state.update {
                            it.copy(
                                book = data,
                                isLoading = false
                            )
                        }
                    }

                    is ResultType.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is ResultType.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        Timber.d("getBookDetail: $result")
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        _sideEffect.tryEmit(BookDetailSideEffect.NavigateBack)
    }

    private fun toggleBookDescriptionExpanded() {
        _state.update { it.copy(isExpanded = !it.isExpanded) }
    }

    private fun onBuyBookClick(url: String) {
        _sideEffect.tryEmit(BookDetailSideEffect.OpenUrl(url))
    }
}