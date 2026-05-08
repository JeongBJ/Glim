package com.jeongbj.presentation.login

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jeongbj.presentation.navigation.Route

fun NavGraphBuilder.loginNav(
    navController: NavHostController
) {
    composable(Route.Login.route) {

        val viewModel: LoginViewModel = hiltViewModel()

        LoginScreen (
            viewModel = viewModel,
            onNavigateHome = {
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Login.route) { inclusive = true }
                }
            }
        )
    }
}