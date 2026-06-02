package com.jeongbj.presentation.feature.book.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.presentation.feature.book.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val books = viewModel.searchBookResult.collectAsLazyPagingItems()
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val listState = rememberLazyListState()

        LaunchedEffect(Unit) {
            viewModel.sideEffect.collect { effect ->
                when(effect) {
                    SearchSideEffect.ScrollToTop -> {
                        listState.scrollToItem(0)
                    }
                }
            }
        }

        SearchContent(
            state = uiState,
            books = books,
            listState = listState,
            onAction = { viewModel.onAction(it) }
        )
    }
}