package com.jeongbj.glim.auth.service

import com.jeongbj.glim.auth.dto.response.AuthTokenResponse
import com.jeongbj.glim.auth.repository.AuthRedisRepository
import com.jeongbj.glim.security.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val jwtProvider: JwtProvider,
    private val authRepository: AuthRedisRepository
) {

    fun createAuthToken(userSeq: Long): AuthTokenResponse {
        val now = System.currentTimeMillis()
        val accessToken = jwtProvider.createAccessToken(userSeq, now)
        val refreshToken = jwtProvider.createRefreshToken(userSeq, now)

        authRepository.save(
            userSeq = userSeq,
            refreshToken = refreshToken
        )

        return AuthTokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun reissueAuthToken(refreshToken: String): AuthTokenResponse {
        if (!jwtProvider.validateRefreshToken(refreshToken)) throw IllegalArgumentException("Invalid RefreshToken")
        val userSeq = jwtProvider.getUserId(refreshToken)
        val savedToken = authRepository.find(userSeq) ?: throw IllegalArgumentException("Not Exist RefreshToken")
        if (savedToken != refreshToken) throw IllegalArgumentException("Invalid RefreshToken!")

        return createAuthToken(userSeq)
    }

    fun logout(userSeq: Long) {
        authRepository.delete(userSeq)
    }
}