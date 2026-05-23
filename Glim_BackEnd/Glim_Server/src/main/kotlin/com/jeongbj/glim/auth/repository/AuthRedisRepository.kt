package com.jeongbj.glim.auth.repository

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class AuthRedisRepository(
    @Value($$"${jwt.refresh-token-validity-in-days}") private val refreshDays: Long,
    private val redisTemplate: StringRedisTemplate
){

    fun save(userSeq: Long, refreshToken: String) {
        redisTemplate.opsForValue().set(
            "refresh:$userSeq",
            refreshToken,
            Duration.ofDays(refreshDays)
        )
    }

    fun find(userSeq: Long): String? =
        redisTemplate.opsForValue().get("refresh:$userSeq")

    fun delete(userSeq: Long) {
        redisTemplate.delete("refresh:$userSeq")
    }
}