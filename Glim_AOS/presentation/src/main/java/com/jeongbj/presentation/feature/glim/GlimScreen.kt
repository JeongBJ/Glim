package com.jeongbj.presentation.feature.glim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.jeongbj.presentation.feature.glim.viewmodel.GlimViewModel
import com.jeongbj.presentation.theme.DarkThemeScreen

@Composable
fun GlimScreen(
    viewModel: GlimViewModel = hiltViewModel()
) {

    val quotes = viewModel.quotes.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsState()

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
        }
    }
}