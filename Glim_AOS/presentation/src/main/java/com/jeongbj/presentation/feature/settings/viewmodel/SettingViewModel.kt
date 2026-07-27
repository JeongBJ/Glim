package com.jeongbj.presentation.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.usecase.AuthUseCases
import com.jeongbj.domain.block.usecase.BlockUseCases
import com.jeongbj.domain.setting.usecase.SettingUseCases
import com.jeongbj.domain.user.model.InfoQuotesType
import com.jeongbj.domain.user.usecase.UserUseCases
import com.jeongbj.presentation.common.paging.BlockedUserPagingSource
import com.jeongbj.presentation.common.paging.QuoteThumbnailPagingSource
import com.jeongbj.presentation.feature.lock.service.LockScreenServiceController
import com.jeongbj.presentation.feature.settings.SettingAction
import com.jeongbj.presentation.feature.settings.SettingSideEffect
import com.jeongbj.presentation.feature.settings.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingUseCases: SettingUseCases,
    private val authUseCases: AuthUseCases,
    private val userUseCases: UserUseCases,
    private val blockUseCases: BlockUseCases,
    private val lockScreenServiceController: LockScreenServiceController,
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
            SettingAction.OnBlockedGlimClicked -> onBlockedGlimClicked()
            SettingAction.OnBlockedUserClicked -> onBlockedUserClicked()
            is SettingAction.OnUnblockQuoteClicked -> onUnblockQuoteClicked(action.quoteSeq)
            is SettingAction.OnUnblockUserClicked -> onUnblockUserClicked(action.userSeq)
            SettingAction.OnLockScreenConfirmClicked -> onLockScreenConfirmClicked()
        }
    }

    private fun onLockScreenConfirmClicked() {
        _state.update { it.copy(settings = it.settings.copy(lockScreenEnabled = true)) }
        updateSettings()
        _sideEffect.tryEmit(SettingSideEffect.OpenBatterySetting)
        lockScreenServiceController.start()
    }

    private fun onUnblockUserClicked(userSeq: Long) {
        viewModelScope.launch {
            blockUseCases.unblockUserUseCase(userSeq).collect { result ->
                if (result is ResultType.Success) {
                    _sideEffect.emit(SettingSideEffect.ShowToast("차단 해제 되었습니다."))
                    blockedUserTrigger.tryEmit(Unit)
                }
            }
        }
    }

    private fun onUnblockQuoteClicked(quoteSeq: Long) {
        viewModelScope.launch {
            blockUseCases.unblockQuoteUseCase(quoteSeq).collect { result ->
                if (result is ResultType.Success) {
                    _sideEffect.emit(SettingSideEffect.ShowToast("차단 해제 되었습니다."))
                    blockedQuoteTrigger.tryEmit(Unit)
                }
            }
        }

    }

    private val blockedUserTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val blockedUsers = blockedUserTrigger.flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                BlockedUserPagingSource(
                    blockUseCases = blockUseCases
                )
            }
        ).flow
    }.cachedIn(viewModelScope)
    private val blockedQuoteTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)


    val blockedQuotes = blockedQuoteTrigger.flatMapLatest {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                QuoteThumbnailPagingSource(
                    blockUseCases = blockUseCases,
                    type = InfoQuotesType.BLOCKED,
                )
            }
        ).flow
    }.cachedIn(viewModelScope)
    private fun onBlockedUserClicked() {
        _sideEffect.tryEmit(SettingSideEffect.ShowBlockedUser)
        blockedUserTrigger.tryEmit(Unit)
    }

    private fun onBlockedGlimClicked() {
        _sideEffect.tryEmit(SettingSideEffect.ShowBlockedGlim)
        blockedQuoteTrigger.tryEmit(Unit)
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
        viewModelScope.launch {
            userUseCases.updateFcmTokenUseCase(pushEnabled).collect {
//                if (it is ResultType.Success) {
//                    val message = if (pushEnabled) "푸시 알림이 활성화 되었습니다." else "푸시 알림이 비활성화 되었습니다."
//                    _sideEffect.emit(SettingSideEffect.ShowToast(message))
//                }
            }
        }
    }

    private fun onLockScreenSwitchToggled(lockScreenEnabled: Boolean) {
        if (lockScreenEnabled) {
            _sideEffect.tryEmit(SettingSideEffect.ShowLockScreenDialog)
        } else {
            _state.update { it.copy(settings = it.settings.copy(lockScreenEnabled = false)) }
            updateSettings()
            lockScreenServiceController.stop()
        }
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