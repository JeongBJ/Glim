package com.jeongbj.domain.auth.repository

import com.jeongbj.domain.auth.model.AuthToken

interface AuthRepository {

    suspend fun refreshAccessToken(refreshToken: String): AuthToken

    suspend fun clearToken()

    suspend fun resign()

    suspend fun logout()
}