package com.jeongbj.glim.auth.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "t_refresh_token")
class RefreshToken(

    @Column(name = "user_seq", nullable = false)
    val userSeq: Long,

    @Column(name = "refresh_token", nullable = false)
    val refreshToken: String,

    @Column(name = "expires_at", nullable = false)
    val expiresAt: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_seq")
    val tokenSeq: Long = 0
)