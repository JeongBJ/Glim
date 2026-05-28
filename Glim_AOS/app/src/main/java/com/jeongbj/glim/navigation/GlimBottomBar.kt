package com.jeongbj.glim.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlimBottomBar(
    currentRoute: String?,
    onNavigate: (Any) -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItem.Home
    )

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets.exclude(WindowInsets.navigationBars),
        modifier = Modifier.height(64.dp)
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    onNavigate(item.route)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        )

                        Text(
                            text = item.label
                        )
                    }
                }
            )
        }
    }
}