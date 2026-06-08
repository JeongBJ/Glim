package com.jeongbj.presentation.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.presentation.common.component.LoadingOverlay
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.home.viewmodel.HomeViewModel
import com.jeongbj.presentation.theme.StatusBarStyle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToQuoteDetail: () -> Unit,
    navigateToBookDetail: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.NavigateToBookDetail -> {
                    navigateToBookDetail(effect.isbn13)
                }
            }
        }
    }

    StatusBarStyle()
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding(),
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.onAction(HomeAction.OnRefreshing) }
    ) {
        Box {
            HomeContent(
                state = uiState,
                onAction = { action ->
                    viewModel.onAction(action)
                }
            )
            if (uiState.isLoading && !uiState.isRefreshing) {
                LoadingOverlay()
            }
        }
    }
}

@Previews
@Composable
fun HomeScreenPreview() {
    HomeContent(
        state = HomeState(),
        onAction = { }
    )
}