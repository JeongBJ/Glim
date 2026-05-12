package com.jeongbj.glim.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-validity-in-minutes}") private val accessMinutes: Long,
    @Value("\${jwt.refresh-token-validity-in-days}") private val refreshDays: Long
) {
    private val accessExpiration = accessMinutes * 60 * 1000
    private val refreshExpiration = refreshDays * 24 * 60 * 60 * 1000

    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    }

    fun createAccessToken(userId: Long, now: Long): String =
        Jwts.builder()
            .subject(userId.toString())
            .issuedAt(Date(now))
            .expiration(Date(now + accessExpiration))
            .signWith(key)
            .compact()

    fun createRefreshToken(now: Long): String =
        Jwts.builder()
            .issuedAt(Date(now))
            .expiration(Date(now + refreshExpiration))
            .signWith(key)
            .compact()

    fun validateToken(token: String): Boolean = runCatching {
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        true
    }.getOrElse { e ->
        when (e) {
            is MalformedJwtException -> logger.info("잘못된 JWT 서명")
            is ExpiredJwtException -> logger.info("만료된 JWT 토큰")
            is UnsupportedJwtException -> logger.info("지원되지 않는 JWT 토큰")
            is IllegalArgumentException -> logger.info("JWT 토큰이 잘못됨")
            else -> logger.info("JWT 검증 실패: ${e.message}")
        }
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

    companion object {
        private val logger = LoggerFactory.getLogger(JwtProvider::class.java)
    }
}