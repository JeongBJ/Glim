package com.jeongbj.presentation.feature.home

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.feature.home.viewmodel.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeNav(
    navigateToQuoteDetail: (Long) -> Unit,
    navigateToBookDetail: (String) -> Unit
) {
    composable<HomeRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(
            viewModel = viewModel,
            navigateToBookDetail = navigateToBookDetail,
            navigateToQuoteDetail = navigateToQuoteDetail
        )
    }
}