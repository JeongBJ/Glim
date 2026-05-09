package com.jeongbj.glim.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jeongbj.glim.R
import com.jeongbj.presentation.feature.login.loginNav

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    val googleClientId = stringResource(R.string.default_web_client_id)
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        loginNav(navController, googleClientId)
    }
}