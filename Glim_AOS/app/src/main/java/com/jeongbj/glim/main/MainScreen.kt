package com.jeongbj.glim.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeongbj.glim.navigation.AppNavGraph
import com.jeongbj.glim.navigation.GlimBottomBar
import com.jeongbj.presentation.feature.book.search.SearchRoute
import com.jeongbj.presentation.feature.home.HomeRoute

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry
        ?.destination
        ?.route

    val bottomBarRoutes = setOf(
        HomeRoute::class,
        SearchRoute::class
    )

    val destination = navBackStackEntry?.destination
    val showBottomBar = bottomBarRoutes.any { route ->
        destination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                GlimBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = navController::navigate
                )
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = if (showBottomBar) {
                Modifier
                    .padding(paddingValues)
            } else {
                Modifier
            }
        )
    }
}