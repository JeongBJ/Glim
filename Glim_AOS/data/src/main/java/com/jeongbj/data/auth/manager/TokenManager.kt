package com.jeongbj.data.auth.manager

import com.jeongbj.data.auth.datasource.RefreshTokenLocalDataSource
import com.jeongbj.data.auth.storage.AccessTokenStorageImpl
import com.jeongbj.domain.auth.model.AuthToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val accessTokenLocalDataSource: AccessTokenStorageImpl,
    private val refreshTokenLocalDataSource: RefreshTokenLocalDataSource
){

    fun saveTokens(token: AuthToken){
        accessTokenLocalDataSource.setAccessToken(token.accessToken)
        refreshTokenLocalDataSource.save(token.refreshToken)
    }

    fun saveAccessToken(accessToken: String){
        accessTokenLocalDataSource.setAccessToken(accessToken)
    }

    fun clearTokens(){
        accessTokenLocalDataSource.clear()
        refreshTokenLocalDataSource.clear()
    }

    fun isLoggedIn(): Boolean {
        return accessTokenLocalDataSource.getAccessToken() != null
    }

    fun getAccessToken(): String? = accessTokenLocalDataSource.getAccessToken()

    fun getRefreshToken(): String? = refreshTokenLocalDataSource.get()
}