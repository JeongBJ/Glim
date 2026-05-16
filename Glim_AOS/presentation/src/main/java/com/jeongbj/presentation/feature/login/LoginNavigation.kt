package com.jeongbj.presentation.feature.login

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login"
const val HOME_ROUTE = "home"

const val PROFILE_ROUTE = "profile"
fun NavGraphBuilder.loginNav(
    navController: NavHostController,
    googleClientId: String
) {
    composable(LOGIN_ROUTE) {
        val viewModel: LoginViewModel = hiltViewModel()

        LoginScreen (
            viewModel = viewModel,
            googleClientId = googleClientId,
            onNavigateHome = {
                navController.navigate(HOME_ROUTE) {
                    popUpTo(LOGIN_ROUTE) { inclusive = true }
                }
            },
            onNavigateProfile = {
                navController.navigate(PROFILE_ROUTE) {
                    popUpTo(LOGIN_ROUTE) { inclusive = true }
                }
            }
        )
    }
}