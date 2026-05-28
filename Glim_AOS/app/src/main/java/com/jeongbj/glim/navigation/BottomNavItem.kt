package com.jeongbj.glim.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.jeongbj.presentation.feature.home.HomeRoute

sealed class BottomNavItem(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = HomeRoute,
        label = "홈",
        icon = Icons.Default.Home
    )
}