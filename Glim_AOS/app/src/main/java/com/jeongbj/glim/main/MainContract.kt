package com.jeongbj.glim.main


sealed interface MainAction {
    data object OnAllowNotification: MainAction
    data object OnNotificationDenied: MainAction
}
sealed interface MainSideEffect {
    data object RequestNotificationPermission: MainSideEffect
    data class ShowToast(val message: String): MainSideEffect
}