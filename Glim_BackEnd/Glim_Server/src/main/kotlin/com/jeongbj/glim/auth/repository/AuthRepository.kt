package com.jeongbj.glim.auth.repository

import com.jeongbj.glim.auth.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface AuthRepository: JpaRepository<RefreshToken, Long> {
    fun findByRefreshToken(token: String): RefreshToken?
    fun deleteAllByUserSeq(userSeq: Long)

    @Transactional
    fun deleteAllByExpiresAtBefore(now: LocalDateTime): Long
}