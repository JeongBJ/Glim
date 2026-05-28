package com.jeongbj.presentation.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.presentation.common.component.LoadingOverlay
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateBookDetail: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    PullToRefreshBox(
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