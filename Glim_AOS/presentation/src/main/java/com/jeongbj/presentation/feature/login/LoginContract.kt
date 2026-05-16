package com.jeongbj.presentation.feature.login

sealed interface LoginEvent {
    data object OnKakaoClick : LoginEvent
    data object OnGoogleClick : LoginEvent
}

sealed interface LoginEffect {
    data object NavigateHome : LoginEffect
    data object NavigateProfile : LoginEffect
    data class ShowMessage(val message: String) : LoginEffect
}

data class LoginState(
    val isLoading: Boolean = false
)