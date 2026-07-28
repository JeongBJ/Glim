package com.jeongbj.glim.user.repository

import com.jeongbj.glim.user.entity.FcmToken
import org.springframework.data.jpa.repository.JpaRepository

interface FcmTokenRepository: JpaRepository<FcmToken, Long> {
    fun findByToken(token: String): FcmToken?
}
