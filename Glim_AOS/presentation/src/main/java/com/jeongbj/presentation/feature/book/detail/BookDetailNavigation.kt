package com.jeongbj.presentation.feature.book.detail

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.feature.book.detail.viewmodel.BookDetailViewModel
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailRoute(
    val isbn13: String
)

fun NavGraphBuilder.bookDetailNav(
    navigateToQuoteDetail: () -> Unit,
    navigateBack: () -> Unit
) {
    composable<BookDetailRoute> {
        val viewModel: BookDetailViewModel = hiltViewModel()
        BookDetailScreen(
            viewModel = viewModel,
            navigateToQuoteDetail = navigateToQuoteDetail,
            navigateBack = navigateBack
        )
    }
}