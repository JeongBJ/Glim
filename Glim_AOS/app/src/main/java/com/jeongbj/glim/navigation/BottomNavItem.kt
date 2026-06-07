package com.jeongbj.glim.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jeongbj.presentation.feature.book.search.SearchRoute
import com.jeongbj.presentation.feature.home.HomeRoute
import com.jeongbj.presentation.feature.post.PostRoute

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

    data object Search: BottomNavItem(
        route = SearchRoute,
        label = "검색",
        icon = Icons.Default.Search
    )

    data object Post: BottomNavItem(
        route = PostRoute,
        label = "포스트",
        icon = Icons.Default.Book
    )
}