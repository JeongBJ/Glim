package com.jeongbj.domain.auth.manager

import com.jeongbj.domain.auth.model.AuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

interface TokenManager {
    fun saveTokens(token: AuthToken)

    fun saveAccessToken(accessToken: String)

    fun clearTokens()

    fun isLoggedIn(): Boolean

    fun requireLogin(): Boolean

    fun currentUserSeq(): Long?

    fun saveUserSeq(userSeq: Long)

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun notifyTokenExpired()

    val sessionEvent: SharedFlow<SessionEvent>
    val loginState: MutableStateFlow<Boolean>

    fun finishStartUp()
}

sealed class SessionEvent {
    data object Expired : SessionEvent()
    data object LoginRequired: SessionEvent()
}
