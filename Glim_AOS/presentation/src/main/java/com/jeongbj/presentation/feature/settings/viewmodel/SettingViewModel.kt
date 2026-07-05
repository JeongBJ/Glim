package com.jeongbj.presentation.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.usecase.AuthUseCases
import com.jeongbj.domain.setting.usecase.SettingUseCases
import com.jeongbj.presentation.feature.settings.SettingAction
import com.jeongbj.presentation.feature.settings.SettingSideEffect
import com.jeongbj.presentation.feature.settings.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCases: SettingUseCases,
    private val authUseCases: AuthUseCases
): ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SettingSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getCurrentSettings()
    }

    fun onAction(action: SettingAction) {
        when (action) {
            is SettingAction.OnAutoLoginSwitchToggled -> onAutoLoginSwitchToggled(action.autoLoginEnabled)
            SettingAction.OnBackClicked -> onBackClicked()
            is SettingAction.OnLockScreenSwitchToggled -> onLockScreenSwitchToggled(action.lockScreenEnabled)
            SettingAction.OnLogoutClicked -> onLogoutClicked()
            is SettingAction.OnPushSwitchToggled -> onPushSwitchToggled(action.pushEnabled)
            SettingAction.OnResignClicked -> onResignClicked()
        }
    }

    private fun onResignClicked() {
        viewModelScope.launch {
            authUseCases.resignUseCase().collect { result ->
                if (result is ResultType.Success) {
                    onAutoLoginSwitchToggled(false)
                    _sideEffect.emit(SettingSideEffect.NavigateLogin)
                }
            }
        }
    }

    private fun onLogoutClicked() {
        viewModelScope.launch {
            authUseCases.logoutUseCase().collect { result ->
                if (result is ResultType.Success) {
                    onAutoLoginSwitchToggled(false)
                    _sideEffect.emit(SettingSideEffect.NavigateLogin)
                }
            }
        }
    }

    private fun onPushSwitchToggled(pushEnabled: Boolean) {
        _state.update { it.copy(settings = it.settings.copy(pushEnabled = pushEnabled)) }
        updateSettings()
    }

    private fun onLockScreenSwitchToggled(lockScreenEnabled: Boolean) {
        _state.update { it.copy(settings = it.settings.copy(lockScreenEnabled = lockScreenEnabled)) }
        updateSettings()
    }

    private fun onAutoLoginSwitchToggled(autoLoginEnabled: Boolean) {
        _state.update { it.copy(settings = it.settings.copy(autoLoginEnabled = autoLoginEnabled)) }
        updateSettings()
    }

    private fun updateSettings() {
        viewModelScope.launch {
            settingUseCases.updateSettingsUseCase(_state.value.settings)
        }
    }

    private fun onBackClicked() =
        _sideEffect.tryEmit(SettingSideEffect.NavigateBack)

    private fun getCurrentSettings() {
        viewModelScope.launch {
            settingUseCases.getSettingsUseCase().collect { result ->
                _state.update { it.copy(settings = result) }
            }
        }
    }
}