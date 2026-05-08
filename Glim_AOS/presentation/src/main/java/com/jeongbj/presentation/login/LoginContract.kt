package com.jeongbj.presentation.login

sealed interface LoginEvent {
    data object OnKakaoClick : LoginEvent
    data object OnGoogleClick : LoginEvent
    data object LoginSuccess : LoginEvent
}

sealed interface LoginEffect {
    data object NavigateHome : LoginEffect
}

data class LoginState(
    val isLoading: Boolean = false
)