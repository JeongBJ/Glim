package com.jeongbj.presentation.feature.glim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.android.extentions.shareGlim
import com.jeongbj.android.extentions.showToast
import com.jeongbj.presentation.common.component.LoadingOverlay
import com.jeongbj.presentation.feature.glim.viewmodel.GlimViewModel
import com.jeongbj.presentation.theme.DarkThemeScreen

@Composable
fun GlimScreen(
    viewModel: GlimViewModel = hiltViewModel(),
    navigateToBookDetail: (String) -> Unit,
    navigateToInfo: (Long) -> Unit
) {

    val quotes = viewModel.quotes.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is GlimSideEffect.NavigateToBookDetail -> navigateToBookDetail(effect.isbn13)
                is GlimSideEffect.ShowToast -> context.showToast(effect.msg)
                is GlimSideEffect.ShareGlim -> context.shareGlim(effect.link)
                is GlimSideEffect.NavigateToInfo -> navigateToInfo(effect.userSeq)
            }
        }
    }

    DarkThemeScreen {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black)
        ) {
            GlimContent(
                state = state,
                onAction = { viewModel.onAction(it) },
                quotes = quotes
            )
            if (state.isLoading) {
                LoadingOverlay()
            }
        }
    }
}