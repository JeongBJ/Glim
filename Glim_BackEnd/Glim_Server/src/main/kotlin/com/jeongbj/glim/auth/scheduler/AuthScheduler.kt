package com.jeongbj.glim.auth.scheduler

import com.jeongbj.glim.auth.repository.AuthRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AuthScheduler(
    private val authRepository: AuthRepository
) {
    @Scheduled(cron = "0 0 4 * * *")
    fun deleteExpiredRefreshToken() {
        authRepository.deleteAllByExpiresAtBefore(
            LocalDateTime.now()
        )
    }
}