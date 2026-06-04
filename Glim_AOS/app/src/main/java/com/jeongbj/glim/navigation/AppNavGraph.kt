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
import com.jeongbj.presentation.feature.home.HomeRoute
import com.jeongbj.presentation.feature.home.homeNav
import com.jeongbj.presentation.feature.login.LoginRoute
import com.jeongbj.presentation.feature.login.QuoteDetailRoute
import com.jeongbj.presentation.feature.login.loginNav
import com.jeongbj.presentation.feature.profile.ProfileRoute
import com.jeongbj.presentation.feature.profile.profileNav

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val googleClientId = stringResource(R.string.default_web_client_id)
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        loginNav(
            googleClientId = googleClientId,
            navigateToHome = { navController.navigate(HomeRoute) {
                popUpTo(LoginRoute) { inclusive = true }
            } },
            navigateToProfile = { navController.navigate(ProfileRoute) }
        )

        profileNav(
            navigateToHome = { navController.navigate(HomeRoute) {
                popUpTo(LoginRoute) { inclusive = true }
            } }
        )

        homeNav(
            navigateToBookDetail = { navController.navigate(BookDetailRoute(isbn13 = it)) },
            navigateToQuoteDetail = { navController.navigate(QuoteDetailRoute) }
        )

        searchNav(
            navigateToBookDetail = { navController.navigate(BookDetailRoute(isbn13 = it)) },
            navigateToQuoteDetail = { }
        )

        bookDetailNav(
            navigateToQuoteDetail = { },
            navigateBack = { navController.popBackStack() }
        )
    }
}