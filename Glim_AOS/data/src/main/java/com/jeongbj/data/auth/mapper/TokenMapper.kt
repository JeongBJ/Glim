package com.jeongbj.data.auth.mapper

import com.jeongbj.data.auth.request.RefreshTokenRequest
import com.jeongbj.data.auth.response.AuthTokenResponse
import com.jeongbj.domain.auth.model.AuthToken

fun AuthTokenResponse.toDomain(): AuthToken = AuthToken(
    accessToken = accessToken.orEmpty(),
    refreshToken = refreshToken.orEmpty()
)

fun AuthToken.toRequest(): RefreshTokenRequest = RefreshTokenRequest(
    refreshToken = refreshToken
)