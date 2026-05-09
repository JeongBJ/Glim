package com.jeongbj.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongbj.domain.auth.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun onClickEvent(event: LoginEvent) {
        viewModelScope.launch {
            authUseCases.logoutUseCase().collectLatest {

            }
        }
    }

    fun googleLogin(idToken: String) {
        Timber.d("googleLogin: $idToken")
    }
}