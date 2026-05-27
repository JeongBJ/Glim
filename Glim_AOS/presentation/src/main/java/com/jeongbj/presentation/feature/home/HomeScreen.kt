package com.jeongbj.presentation.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.presentation.feature.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateBookDetail: () -> Unit
) {

}