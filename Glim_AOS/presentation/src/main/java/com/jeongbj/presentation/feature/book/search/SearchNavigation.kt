package com.jeongbj.presentation.feature.book.search

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.feature.book.search.viewmodel.SearchViewModel
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavGraphBuilder.searchNav(
    navigateToQuoteDetail: () -> Unit,
    navigateToBookDetail: () -> Unit
) {
    composable<SearchRoute> {
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreen(viewModel)
    }
}