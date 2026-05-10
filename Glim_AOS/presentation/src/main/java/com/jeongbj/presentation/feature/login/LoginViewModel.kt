package com.jeongbj.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.login.usecase.LoginUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun onClickEvent(event: LoginEvent, idToken: String) {
        when (event) {
            LoginEvent.OnGoogleClick -> login(loginUseCases.googleLoginUseCase(idToken))
            LoginEvent.OnKakaoClick -> login(loginUseCases.kakaoLoginUseCase(idToken))
            else -> { }
        }
    }

    private fun login(flow: Flow<ResultType<Unit>>) {
        viewModelScope.launch {
            flow.collectLatest {
                if (it is ResultType.Success) {
                    _event.emit(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}