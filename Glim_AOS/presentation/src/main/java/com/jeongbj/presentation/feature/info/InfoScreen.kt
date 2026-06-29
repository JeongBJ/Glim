package com.jeongbj.presentation.feature.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.presentation.feature.info.viewmodel.InfoViewModel
import com.jeongbj.presentation.theme.StatusBarStyle

@Composable
fun InfoScreen(
    viewModel: InfoViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val quotes = viewModel.quotes.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->

        }
    }

    StatusBarStyle()
    Box(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) {
        InfoContent(
            state = state,
            onAction = { viewModel.onAction(it) },
            quotes = quotes
        )
    }
}