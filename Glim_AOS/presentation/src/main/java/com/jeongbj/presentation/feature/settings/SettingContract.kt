package com.jeongbj.presentation.feature.settings

import com.jeongbj.domain.setting.model.Settings

data class SettingState(
    val isLoading: Boolean = false,
    val settings: Settings = Settings(),
)

sealed interface SettingAction {
    data object OnBackClicked: SettingAction
    data class OnAutoLoginSwitchToggled(val autoLoginEnabled: Boolean): SettingAction
    data class OnPushSwitchToggled(val pushEnabled: Boolean): SettingAction
    data class OnLockScreenSwitchToggled(val lockScreenEnabled: Boolean): SettingAction
    data object OnLogoutClicked: SettingAction
    data object OnResignClicked: SettingAction
    data object OnBlockedGlimClicked: SettingAction
    data object OnBlockedUserClicked: SettingAction
    data class OnUnblockUserClicked(val userSeq: Long): SettingAction
    data class OnUnblockQuoteClicked(val quoteSeq: Long): SettingAction
    data object OnLockScreenConfirmClicked: SettingAction
}

sealed interface SettingSideEffect {
    data class ShowToast(val message: String): SettingSideEffect
    data object NavigateBack: SettingSideEffect
    data object NavigateLogin: SettingSideEffect
    data object ShowBlockedGlim: SettingSideEffect
    data object ShowBlockedUser: SettingSideEffect
    data object ShowLockScreenDialog: SettingSideEffect
    data object OpenBatterySetting: SettingSideEffect
}