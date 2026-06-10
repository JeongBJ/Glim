package com.jeongbj.presentation.feature.book.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.feature.book.search.viewmodel.SearchViewModel
import com.jeongbj.presentation.theme.StatusBarStyle

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToQuoteDetail: () -> Unit,
    popBackStack: (() -> Unit)? = null,
    navigateToBookDetail: (String) -> Unit,
    onQuoteBookSelected: ((Book) -> Unit)? = null
) {
    val books = viewModel.searchBookResult.collectAsLazyPagingItems()
    val uiState by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()


    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when(effect) {
                is SearchSideEffect.ScrollToTop -> {
                    listState.scrollToItem(0)
                    gridState.scrollToItem(0)
                }

                is SearchSideEffect.NavigateToBookDetail -> {
                    if (onQuoteBookSelected == null) navigateToBookDetail(effect.book.isbn13)
                    else onQuoteBookSelected(effect.book)
                }
            }
        }
    }

    StatusBarStyle()
    Box(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) {
        SearchContent(
            state = uiState,
            books = books,
            listState = listState,
            gridState = gridState,
            onAction = { viewModel.onAction(it) }
        )
    }
}