package com.jeongbj.presentation.feature.profile

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

const val PROFILE_ROUTE = "profile"

@Serializable
data object ProfileRoute

fun NavGraphBuilder.profileNav(
    navigateToHome: () -> Unit
) {
    composable<ProfileRoute> {
        val viewModel: ProfileViewModel = hiltViewModel()

        ProfileScreen(
            viewModel = viewModel,
            onNavigateHome = navigateToHome
        )
    }
}