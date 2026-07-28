package com.jeongbj.glim.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.jeongbj.data.fcm.manager.FcmTokenManager
import com.jeongbj.domain.auth.manager.TokenManager
import com.jeongbj.domain.setting.usecase.SettingUseCases
import com.jeongbj.domain.user.usecase.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val fcmTokenManager: FcmTokenManager,
    private val settingUseCases: SettingUseCases,
    private val userUseCases: UserUseCases
) : ViewModel() {

    val sessionEvent = tokenManager.sessionEvent

    private val _sideEffect = MutableSharedFlow<MainSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        saveFcmToken()
    }

    fun onAction(action: MainAction) {
        when (action) {
            MainAction.OnAllowNotification -> onAllowNotification()
            MainAction.OnNotificationDenied -> onNotificationDenied()
        }
    }

    private fun onNotificationDenied() =
        _sideEffect.tryEmit(MainSideEffect.ShowToast("알림 설정은 내정보 - 설정에서 다시 설정할 수 있어요"))

    private fun onAllowNotification() {
        viewModelScope.launch {
            val settings = settingUseCases.getSettingsUseCase().first()
            settingUseCases.updateSettingsUseCase(settings.copy(pushEnabled = true))
            val token = fcmTokenManager.getFcmToken().first()
            if (token != null) {
                fcmTokenManager.saveFcmToken(token.token, true)
            }
        }
    }

    private fun saveFcmToken() {
        viewModelScope.launch {
            val currentToken = fcmTokenManager.getFcmToken().first()
            val token = FirebaseMessaging.getInstance().token.await()
            val settings = settingUseCases.getSettingsUseCase().first()
            fcmTokenManager.saveFcmToken(token, settings.pushEnabled)
            if (currentToken == null) {
                _sideEffect.emit(MainSideEffect.RequestNotificationPermission)
                return@launch
            }
        }
    }

    fun isLoggedIn(): Boolean =
        tokenManager.isLoggedIn()

    fun requireLogin() =
        tokenManager.requireLogin()


}