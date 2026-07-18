package com.jeongbj.data.auth.manager

import com.jeongbj.data.auth.datasource.RefreshTokenLocalDataSource
import com.jeongbj.data.auth.storage.AccessTokenStorageImpl
import com.jeongbj.domain.auth.manager.SessionEvent
import com.jeongbj.domain.auth.manager.TokenManager
import com.jeongbj.domain.auth.model.AuthToken
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManagerImpl @Inject constructor(
    private val accessTokenLocalDataSource: AccessTokenStorageImpl,
    private val refreshTokenLocalDataSource: RefreshTokenLocalDataSource
): TokenManager {

    private var userSeq: Long? = null
    override fun saveTokens(token: AuthToken){
        accessTokenLocalDataSource.setAccessToken(token.accessToken)
        refreshTokenLocalDataSource.save(token.refreshToken)
    }

    override fun saveAccessToken(accessToken: String){
        accessTokenLocalDataSource.setAccessToken(accessToken)
    }

    override fun clearTokens(){
        accessTokenLocalDataSource.clear()
        refreshTokenLocalDataSource.clear()
    }

    override fun isLoggedIn(): Boolean {
        return accessTokenLocalDataSource.getAccessToken() != null
    }

    override fun requireLogin(): Boolean =
        _sessionEvent.tryEmit(SessionEvent.LoginRequired)

    override fun currentUserSeq(): Long? = userSeq

    override fun saveUserSeq(userSeq: Long) {
        this.userSeq = userSeq
    }


    override fun getAccessToken(): String? = accessTokenLocalDataSource.getAccessToken()

    override fun getRefreshToken(): String? = refreshTokenLocalDataSource.get()

    private val _sessionEvent = MutableSharedFlow<SessionEvent>(extraBufferCapacity = 1)
    override val sessionEvent get() = _sessionEvent.asSharedFlow()

    override fun notifyTokenExpired() {
        _sessionEvent.tryEmit(SessionEvent.Expired)
    }

    override val loginState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override fun finishStartUp() {
        loginState.value = false
    }
}