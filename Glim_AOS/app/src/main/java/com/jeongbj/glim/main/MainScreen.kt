package com.jeongbj.glim.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeongbj.android.extentions.showToast
import com.jeongbj.domain.auth.manager.SessionEvent
import com.jeongbj.glim.navigation.AppNavGraph
import com.jeongbj.glim.navigation.GlimBottomBar
import com.jeongbj.presentation.common.notification.rememberNotificationPermissionState
import com.jeongbj.presentation.feature.book.search.SearchRoute
import com.jeongbj.presentation.feature.glim.GlimRoute
import com.jeongbj.presentation.feature.home.HomeRoute
import com.jeongbj.presentation.feature.info.InfoRoute
import com.jeongbj.presentation.feature.login.LoginRoute

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val context = LocalContext.current

    val bottomBarRoutes = setOf(
        HomeRoute::class,
        SearchRoute::class,
        GlimRoute::class,
        InfoRoute::class
    )

    val destination = navBackStackEntry?.destination
    val showBottomBar = bottomBarRoutes.any { route ->
        destination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }

    val isGlimRoute = destination?.hierarchy?.any {
        it.hasRoute(GlimRoute::class)
    } == true

    val permissionState = rememberNotificationPermissionState(
        onResult = { granted ->
            if (granted) viewModel.onAction(MainAction.OnAllowNotification)
            else viewModel.onAction(MainAction.OnNotificationDenied)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.sessionEvent.collect { event ->
            when (event) {
                SessionEvent.Expired,
                SessionEvent.LoginRequired -> {
                    navController.navigate(LoginRoute) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                MainSideEffect.RequestNotificationPermission -> {
                    if (!permissionState.isEnabled) permissionState.requestPermission()
                }
                is MainSideEffect.ShowToast -> context.showToast(sideEffect.message)

            }
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                GlimBottomBar(
                    destination = destination,
                    onNavigate = { route ->
                        if (!viewModel.isLoggedIn()) {
                            viewModel.requireLogin()
                            return@GlimBottomBar
                        }
                        navController.navigate(route)
                    },
                    isDarkMode = isGlimRoute
                )
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = if (showBottomBar) {
                Modifier
                    .padding(bottom = paddingValues.calculateBottomPadding())
            } else {
                Modifier
            }
        )
    }
}