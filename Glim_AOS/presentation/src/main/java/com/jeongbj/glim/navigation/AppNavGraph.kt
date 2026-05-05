package com.jeongbj.glim.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jeongbj.glim.feature.login.loginNav

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        loginNav(navController)
    }
}