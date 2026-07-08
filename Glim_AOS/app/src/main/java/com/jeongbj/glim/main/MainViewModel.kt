package com.jeongbj.glim.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.data.fcm.manager.FcmTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val fcmTokenManager: FcmTokenManager
) : ViewModel() {

    val sessionEvent = tokenManager.sessionEvent

    init {
        saveFcmToken()
    }

    private fun saveFcmToken() {
        viewModelScope.launch {
            val token = FirebaseMessaging.getInstance().token.await()
            fcmTokenManager.saveFcmToken(token)
        }
    }

    fun isLoggedIn(): Boolean =
        tokenManager.isLoggedIn()

    fun requireLogin() =
        tokenManager.requireLogin()


}