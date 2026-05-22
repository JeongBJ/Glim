package com.jeongbj.glim.auth.service

import com.jeongbj.glim.auth.dto.response.AuthTokenResponse
import com.jeongbj.glim.auth.entity.RefreshToken
import com.jeongbj.glim.auth.repository.AuthRepository
import com.jeongbj.glim.security.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId

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

        val claims = jwtProvider.getClaims(refreshToken)

        authRepository.save(RefreshToken(
            userSeq = userSeq,
            refreshToken = refreshToken,
            expiresAt = claims.expiration
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
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

        if (!jwtProvider.validateToken(refreshToken)) {
            authRepository.delete(entity)
            throw IllegalArgumentException("Invalid RefreshToken")
        }

        authRepository.delete(entity)
        return createAuthToken(entity.userSeq)
    }

    fun logout(userSeq: Long) {
        authRepository.deleteAllByUserSeq(userSeq)
    }
}