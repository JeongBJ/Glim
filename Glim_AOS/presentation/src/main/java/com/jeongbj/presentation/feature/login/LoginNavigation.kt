package com.jeongbj.presentation.feature.login

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute
@Serializable
data object QuoteDetailRoute {
}

fun NavGraphBuilder.loginNav(
    navigateToHome: () -> Unit,
    navigateToProfile: () -> Unit,
    googleClientId: String
) {
    composable<LoginRoute> {
        val viewModel: LoginViewModel = hiltViewModel()

        LoginScreen(
            viewModel = viewModel,
            googleClientId = googleClientId,
            onNavigateHome = navigateToHome,
            onNavigateProfile = navigateToProfile
        )
    }
}