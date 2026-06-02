package com.jeongbj.presentation.feature.book.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.domain.book.usecase.BookUseCases
import com.jeongbj.presentation.feature.book.search.SearchAction
import com.jeongbj.presentation.feature.book.search.SearchMode
import com.jeongbj.presentation.feature.book.search.SearchSideEffect
import com.jeongbj.presentation.feature.book.search.SearchState
import com.jeongbj.presentation.feature.book.search.SearchTab
import com.jeongbj.presentation.feature.book.search.paging.BookPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,

    ) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SearchSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchClick -> {
                search()
                _state.update { it.copy(searchMode = SearchMode.RESULT) }
            }
            is SearchAction.OnTextChanged -> {
                _state.update { it.copy(query = action.query) }
            }
            is SearchAction.OnSelectedTabChanged -> {
                _state.update { it.copy(selectedTab = action.selectedTab) }
            }
            is SearchAction.OnFilterSelected -> {
                _state.update { it.copy(searchFilter = action.filter) }
                search()
            }

            is SearchAction.OnBackClick -> TODO()
            is SearchAction.OnBookClick -> TODO()
            is SearchAction.OnBuyBookClick -> TODO()
            is SearchAction.OnQueryClick -> TODO()
            is SearchAction.OnQuoteClick -> TODO()
            is SearchAction.OnRegisterQuoteClick -> TODO()
        }
    }
    private val bookSearchTrigger = MutableSharedFlow<Unit>()
    val searchBookResult =
            bookSearchTrigger
            .flatMapLatest {
                val currentState = state.value
                Pager(
                    config = PagingConfig(pageSize = 20, initialLoadSize = 20),
                    pagingSourceFactory = {
                        BookPagingSource(
                            searchBookUseCase = bookUseCases.searchBookUseCase,
                            query = currentState.query,
                            type = currentState.searchFilter.type,
                            totalElements = { count ->
                                _state.update { it.copy(totalBookElements = count) }
                            }
                        )
                    }
                ).flow
            }
            .cachedIn(viewModelScope)

    private val quoteSearchTrigger = MutableSharedFlow<Unit>()
    val searchQuoteResult =
        quoteSearchTrigger
            .flatMapLatest {
                val currentState = state.value
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        BookPagingSource(
                            searchBookUseCase = bookUseCases.searchBookUseCase,
                            query = currentState.query,
                            type = currentState.searchFilter.type,
                            totalElements = { }
                        )
                    }
                ).flow
            }
            .cachedIn(viewModelScope)

    private fun search() {
        viewModelScope.launch {
            _sideEffect.emit(SearchSideEffect.ScrollToTop)
            when (state.value.selectedTab) {
                SearchTab.BOOK -> {
                    bookSearchTrigger.emit(Unit)
                }
                SearchTab.QUOTE -> {
                    quoteSearchTrigger.emit(Unit)
                }
            }
        }
    }
}