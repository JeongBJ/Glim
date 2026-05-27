package com.jeongbj.presentation.feature.home

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.feature.home.viewmodel.HomeViewModel
import kotlinx.serialization.Serializable

const val HOME_ROUTE = "home"

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeNav(
    navigateToQuoteDetail: () -> Unit,
    navigateToBookDetail: () -> Unit
) {
    composable<HomeRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel, onNavigateBookDetail = navigateToBookDetail)
    }
}