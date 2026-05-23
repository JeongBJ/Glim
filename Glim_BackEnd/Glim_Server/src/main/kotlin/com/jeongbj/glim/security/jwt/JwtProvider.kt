package com.jeongbj.glim.security.jwt

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-validity-in-minutes}") private val accessMinutes: Long,
    @Value("\${jwt.refresh-token-validity-in-days}") private val refreshDays: Long
) {
    private val accessExpiration = Duration.ofMinutes(accessMinutes).toMillis()
    private val refreshExpiration = Duration.ofDays(refreshDays).toMillis()

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    private val logger = KotlinLogging.logger {  }

    fun createAccessToken(userSeq: Long, now: Long): String =
        Jwts.builder()
            .subject(userSeq.toString())
            .claim("type", TokenType.ACCESS)
            .issuedAt(Date(now))
            .expiration(Date(now + accessExpiration))
            .signWith(key)
            .compact()

    fun createRefreshToken(userSeq: Long, now: Long): String {
        return Jwts.builder()
            .subject(userSeq.toString())
            .claim("type", TokenType.REFRESH)
            .issuedAt(Date(now))
            .expiration(Date(now + refreshExpiration))
            .signWith(key)
            .compact()
    }


    fun validateAccessToken(token: String): Boolean = runCatching {
        val claims = getClaims(token)
        claims["type"] == TokenType.ACCESS
    }.getOrElse { e ->
        when (e) {
            is MalformedJwtException -> logger.info { "잘못된 JWT 서명" }
            is ExpiredJwtException -> logger.info { "만료된 JWT 토큰" }
            is UnsupportedJwtException -> logger.info { "지원되지 않는 JWT 토큰" }
            is IllegalArgumentException -> logger.info { "JWT 토큰이 잘못됨" }
            else -> logger.info { "JWT 검증 실패: ${e.message}" }
        }
        false
    }

    fun validateRefreshToken(token: String): Boolean = runCatching {
        val claims = getClaims(token)

        claims["type"] == TokenType.REFRESH
    }.getOrElse { e ->
        logger.info { "Refresh Token 검증 실패: ${e.message}" }
        false
    }

    fun getUserId(token: String): Long =
        getClaims(token).subject.toLong()

    private fun getClaims(token: String): Claims = runCatching {
        Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token).payload
    }.getOrElse { e ->
        if (e is ExpiredJwtException) e.claims
        else throw e
    }

    enum class TokenType {
        ACCESS,
        REFRESH
    }
}