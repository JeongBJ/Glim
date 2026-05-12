package com.jeongbj.glim.auth.service

import com.jeongbj.glim.auth.dto.response.AuthTokenResponse
import com.jeongbj.glim.auth.entity.RefreshToken
import com.jeongbj.glim.auth.repository.AuthRepository
import com.jeongbj.glim.security.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthService(
    private val jwtProvider: JwtProvider,
    private val authRepository: AuthRepository
) {

    fun createAuthToken(userSeq: Long): AuthTokenResponse {
        val now = System.currentTimeMillis()
        val accessToken = jwtProvider.createAccessToken(userSeq, now)
        val refreshToken = jwtProvider.createRefreshToken(now)
        authRepository.save(RefreshToken(
            userSeq = userSeq,
            refreshToken = refreshToken,
            expiresAt = LocalDateTime.now()
        ))

        return AuthTokenResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun reissueAuthToken(refreshToken: String): AuthTokenResponse {
        val entity = authRepository.findByRefreshToken(refreshToken)
            ?: throw IllegalArgumentException("RefreshToken not found")

        if (entity.expiresAt.isBefore(LocalDateTime.now())) {
            authRepository.delete(entity)
            throw IllegalArgumentException("RefreshToken expired")
        }

        return createAuthToken(entity.userSeq)
    }

    fun logout(userSeq: Long) {
        authRepository.deleteAllByUserSeq(userSeq)
    }
}