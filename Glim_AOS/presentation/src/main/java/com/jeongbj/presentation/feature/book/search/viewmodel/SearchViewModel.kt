package com.jeongbj.presentation.feature.book.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.book.model.Book
import com.jeongbj.domain.book.model.QueryType
import com.jeongbj.domain.book.usecase.BookUseCases
import com.jeongbj.presentation.common.paging.BookPagingSource
import com.jeongbj.presentation.feature.book.search.SearchAction
import com.jeongbj.presentation.feature.book.search.SearchMode
import com.jeongbj.presentation.feature.book.search.SearchSideEffect
import com.jeongbj.presentation.feature.book.search.SearchState
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

    init {
        getQueryRanking()
        getRecentQuery()
    }

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

            is SearchAction.OnBookClick -> {
                navigateToBookDetail(action.book)
            }
            is SearchAction.OnQueryClick -> onQueryClicked(action.query)
            is SearchAction.OnQuoteClick -> onQuoteClicked(action.quoteSeq)
            is SearchAction.OnBackClick -> onBackClicked()
            SearchAction.OnClearHistoryClicked -> onClearHistoryClicked()
        }
    }

    private fun onClearHistoryClicked() {
        viewModelScope.launch {
            bookUseCases.clearRecentQueryUseCase()
            _state.update { it.copy(recentQuery = listOf()) }
        }
    }

    private fun getRecentQuery() {
        viewModelScope.launch {
            bookUseCases.getRecentQueryUseCase().collect { result ->
                _state.update { it.copy(recentQuery = result) }
            }
        }
    }

    private fun getQueryRanking() {
        viewModelScope.launch {
            val queryType = _state.value.selectedTab
            bookUseCases.getQueryRankUseCase(queryType).collect { result ->
                if (result is ResultType.Success) {
                    _state.update { it.copy(popularQuery = result.data) }
                }
            }
        }
    }

    private fun onQueryClicked(query: String) {
        _state.update {
            it.copy(
                query = query,
                searchMode = SearchMode.RESULT,
                selectedTab = QueryType.BOOK
            )
        }
        search()
    }


    private fun onBackClicked() =
        _sideEffect.tryEmit(SearchSideEffect.NavigateBack)
    private fun onQuoteClicked(quoteSeq: Long) =
        _sideEffect.tryEmit(SearchSideEffect.NavigateToQuoteDetail(quoteSeq))

    private fun navigateToBookDetail(book: Book) {
        viewModelScope.launch {
            _sideEffect.emit(SearchSideEffect.NavigateToBookDetail(book))
        }
    }

    private val bookSearchTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
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
            _sideEffect.tryEmit(SearchSideEffect.ScrollToTop)
            when (state.value.selectedTab) {
                QueryType.BOOK -> {
                    bookSearchTrigger.tryEmit(Unit)
                }
                QueryType.QUOTE -> {
                    quoteSearchTrigger.tryEmit(Unit)
                }
            }
            saveRecentQuery()
        }
    }
    
    private suspend fun saveRecentQuery() {
        bookUseCases.saveRecentQueryUseCase(
            query = _state.value.query,
            queryType = _state.value.selectedTab
        )
    }
}