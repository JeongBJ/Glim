package com.jeongbj.presentation.navigation

sealed interface Route {
    val route: String

    data object Login : Route {
        override val route = "login"
    }

    data object Home : Route {
        override val route = "home"
    }
}