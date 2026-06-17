package com.jeongbj.glim.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun GlimBottomBar(
    destination: NavDestination?,
    onNavigate: (Any) -> Unit,
    isDarkMode: Boolean = false
) {
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Post,
        BottomNavItem.Glim
    )

    val (backgroundColor, iconTint) =
        if (isDarkMode) {
            Color(0xFF1C1B1F) to MaterialTheme.colorScheme.surface
        } else {
            Color.White to MaterialTheme.colorScheme.onSurface
        }

    NavigationBar(
        containerColor = backgroundColor,
        windowInsets = NavigationBarDefaults.windowInsets.exclude(WindowInsets.navigationBars),
        modifier = Modifier.height(64.dp)
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = destination?.hierarchy?.any {
                it.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                alwaysShowLabel = false,
                selected = false,
                onClick = {
                    onNavigate(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            tint = if (isSelected) iconTint else iconTint.copy(alpha = 0.4f)
                        )

                        Text(
                            text = item.label,
                            color = if (isSelected) iconTint else iconTint.copy(alpha = 0.4f)
                        )
                    }
                }
            )
        }
    }
}