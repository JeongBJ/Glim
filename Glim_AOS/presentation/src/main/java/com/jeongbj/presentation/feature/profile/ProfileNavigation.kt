package com.jeongbj.presentation.feature.profile

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.common.model.UserUI
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

fun NavGraphBuilder.profileNav(
    navController: NavController,
    navigateToHome: () -> Unit,
    popBackStack: () -> Unit,
) {
    composable<ProfileRoute> {
        val viewModel: ProfileViewModel = hiltViewModel()
        val user = navController.previousBackStackEntry?.savedStateHandle?.get<UserUI>("user")
        viewModel.init(user)
        ProfileScreen(
            viewModel = viewModel,
            onNavigateHome = navigateToHome,
            popBackStack = popBackStack,
            navigateToInfo = {
                navController.previousBackStackEntry?.savedStateHandle?.set("profile_updated", true)
                popBackStack()
            }
        )
    }
}