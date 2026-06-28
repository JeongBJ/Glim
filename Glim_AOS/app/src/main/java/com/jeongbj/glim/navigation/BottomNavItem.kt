package com.jeongbj.glim.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.jeongbj.presentation.R
import com.jeongbj.presentation.feature.book.search.SearchRoute
import com.jeongbj.presentation.feature.glim.GlimRoute
import com.jeongbj.presentation.feature.home.HomeRoute
import com.jeongbj.presentation.feature.info.InfoRoute
import com.jeongbj.presentation.feature.post.PostRoute

sealed class BottomNavItem(
    val route: Any,
    val label: String,
    val icon: Int
) {
    data object Home : BottomNavItem(
        route = HomeRoute,
        label = "홈",
        icon = R.drawable.ic_home
    )

    data object Search: BottomNavItem(
        route = SearchRoute,
        label = "검색",
        icon = R.drawable.ic_search
    )

    data object Post: BottomNavItem(
        route = PostRoute,
        label = "포스트",
        icon = R.drawable.ic_post
    )

    data object Glim: BottomNavItem(
        route = GlimRoute(),
        label = "글:림",
        icon = R.drawable.ic_book_ribbon
    )

    data object Info: BottomNavItem(
        route = InfoRoute,
        label = "내정보",
        icon = R.drawable.ic_person
    )
}

sealed interface NavIcon {
    data class Vector(val imageVector: ImageVector) : NavIcon
    data class Resource(val resId: Int) : NavIcon
}