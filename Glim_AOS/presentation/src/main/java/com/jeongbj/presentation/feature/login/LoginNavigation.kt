package com.jeongbj.presentation.feature.login

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login"
const val HOME_ROUTE = "home"
fun NavGraphBuilder.loginNav(
    navController: NavHostController,
    googleClientId: String
) {
    composable(LOGIN_ROUTE) {
        val viewModel: LoginViewModel = hiltViewModel()

        LoginScreen (
            viewModel = viewModel,
            onNavigateHome = {
                navController.navigate(HOME_ROUTE) {
                    popUpTo(LOGIN_ROUTE) { inclusive = true }
                }
            },
            googleClientId = googleClientId
        )
    }
}