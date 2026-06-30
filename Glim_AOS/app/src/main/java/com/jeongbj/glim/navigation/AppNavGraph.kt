package com.jeongbj.glim.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jeongbj.glim.R
import com.jeongbj.presentation.feature.book.detail.BookDetailRoute
import com.jeongbj.presentation.feature.book.detail.bookDetailNav
import com.jeongbj.presentation.feature.book.search.searchNav
import com.jeongbj.presentation.feature.glim.GlimRoute
import com.jeongbj.presentation.feature.glim.glimNav
import com.jeongbj.presentation.feature.home.HomeRoute
import com.jeongbj.presentation.feature.home.homeNav
import com.jeongbj.presentation.feature.info.infoNav
import com.jeongbj.presentation.feature.login.LoginRoute
import com.jeongbj.presentation.feature.login.loginNav
import com.jeongbj.presentation.feature.post.PostRoute
import com.jeongbj.presentation.feature.post.postNav
import com.jeongbj.presentation.feature.profile.ProfileRoute
import com.jeongbj.presentation.feature.profile.profileNav

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val googleClientId = stringResource(R.string.default_web_client_id)
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LoginRoute
    ) {
        loginNav(
            googleClientId = googleClientId,
            navigateToHome = {
                navController.navigate(HomeRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            navigateToProfile = { navController.navigate(ProfileRoute) }
        )

        profileNav(
            navController = navController,
            navigateToHome = {
                navController.navigate(HomeRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
            popBackStack = { navController.popBackStack() }
        )

        homeNav(
            navigateToBookDetail = {
                navController.previousBackStackEntry?.savedStateHandle?.set("profile_updated", true)
                navController.navigate(BookDetailRoute(isbn13 = it))
            },
            navigateToQuoteDetail = { navController.navigate(GlimRoute(quoteSeq = it)) }
        )

        searchNav(
            navigateToBookDetail = { navController.navigate(BookDetailRoute(isbn13 = it)) },
            navigateToQuoteDetail = { navController.navigate(GlimRoute(it)) },
            popBackStack = { navController.popBackStack() }
        )

        bookDetailNav(
            navigateToQuoteDetail = { navController.navigate(GlimRoute(quoteSeq = it)) },
            navigateBack = { navController.popBackStack() },
            navigateToPost = { navController.navigate(PostRoute) }
        )

        postNav(
            navigateBack = { navController.popBackStack() },
        )

        glimNav(
            navigateToBookDetail = { navController.navigate(BookDetailRoute(it)) }
        )

        infoNav(
            navigateToProfile = {
                navController.currentBackStackEntry?.savedStateHandle?.set("user", it)
                navController.navigate(ProfileRoute)
            },
            navigateToQuoteDetail = { navController.navigate(GlimRoute(it)) },
            navigateToSettings = { }
        )
    }
}