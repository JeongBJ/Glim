package com.jeongbj.glim.main

import androidx.lifecycle.ViewModel
import com.jeongbj.data.auth.manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    val sessionEvent = tokenManager.sessionEvent

    fun isLoggedIn(): Boolean =
        tokenManager.isLoggedIn()

    fun requireLogin() =
        tokenManager.requireLogin()
}