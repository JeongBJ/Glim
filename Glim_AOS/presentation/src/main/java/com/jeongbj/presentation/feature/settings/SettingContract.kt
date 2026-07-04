package com.jeongbj.presentation.feature.settings

data class SettingState(
    val isLoading: Boolean = false,
    val isPushEnabled: Boolean = false,

)

sealed interface SettingAction {
    data object OnBackClicked: SettingAction
    data class OnPushSwitchToggled(val pushEnabled: Boolean): SettingAction
    data object OnLogoutClicked: SettingAction
}

sealed interface SettingSideEffect {

}