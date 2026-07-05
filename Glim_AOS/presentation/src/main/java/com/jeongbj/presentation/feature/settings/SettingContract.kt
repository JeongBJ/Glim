package com.jeongbj.presentation.feature.settings

data class SettingState(
    val isLoading: Boolean = false,
    val isPushEnabled: Boolean = false,

)

sealed interface SettingAction {
    data object OnBackClicked: SettingAction
    data class OnAutoLoginSwitchToggled(val pushEnabled: Boolean): SettingAction
    data class OnPushSwitchToggled(val pushEnabled: Boolean): SettingAction
    data class OnLockScreenSwitchToggled(val pushEnabled: Boolean): SettingAction
    data object OnLogoutClicked: SettingAction
    data object OnResignClicked: SettingAction
}

sealed interface SettingSideEffect {

}