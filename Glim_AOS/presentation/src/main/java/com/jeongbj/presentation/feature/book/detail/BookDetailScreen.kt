package com.jeongbj.presentation.feature.book.detail

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.detail.viewmodel.BookDetailViewModel
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel = hiltViewModel(),
    navigateToQuoteDetail: (Long) -> Unit,
    navigateToPost: () -> Unit,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is BookDetailSideEffect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                    context.startActivity(intent)
                }

                is BookDetailSideEffect.NavigateBack -> {
                    navigateBack()
                }

                is BookDetailSideEffect.ShowGlimItem -> {
                    navigateToQuoteDetail(effect.quoteSeq)
                }

                BookDetailSideEffect.NavigateToPost -> {
                    navigateToPost()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()
        .statusBarsPadding()
    ) {
        BookDetailContent (
            state = state,
            onAction = { viewModel.onAction(it) }
        )
    }

}

@Previews
@Composable
fun BookDetailPreview() {
    GlimTheme {
        BookDetailContent(
            BookDetailState()
        ) { }
    }
}