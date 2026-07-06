package com.jeongbj.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.auth.usecase.RefreshAccessTokenUseCase
import com.jeongbj.domain.setting.usecase.SettingUseCases
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.usecase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
    private val settingUseCases: SettingUseCases,
    private val refreshAccessTokenUseCase: RefreshAccessTokenUseCase,
) : ViewModel() {

    private val _loginEffect = MutableSharedFlow<LoginEffect>()
    val loginEffect = _loginEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            val settings = settingUseCases.getSettingsUseCase().first()
            if (settings.autoLoginEnabled) {
                refreshAccessTokenUseCase().collect { result ->
                    if (result is ResultType.Success) {
                        _loginEffect.emit(LoginEffect.NavigateHome)
                    }
                }
            }
        }
    }


    fun onClickEvent(event: LoginEvent, idToken: String) {
        when (event) {
            LoginEvent.OnGoogleClick -> login(loginUseCases.googleLoginUseCase(idToken))
            LoginEvent.OnKakaoClick -> login(loginUseCases.kakaoLoginUseCase(idToken))
        }
    }

    private fun login(flow: Flow<ResultType<User>>) {
        viewModelScope.launch {
            flow.collectLatest { result ->
                when (result) {
                    is ResultType.Success -> {
                        if (result.data.nickname.isBlank()) {
                            _loginEffect.emit(LoginEffect.NavigateProfile)
                        } else {
                            _loginEffect.emit(LoginEffect.NavigateHome)
                        }
                    }

                    is ResultType.Error -> {
                        Timber.e(result.exception, "login: ")
                        _loginEffect.emit(LoginEffect.ShowMessage("로그인에 실패했습니다."))
                    }

                    else -> Unit
                }
            }
        }
    }
}