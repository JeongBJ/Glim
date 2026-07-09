package com.jeongbj.glim.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(name = "t_fcm_token",
    uniqueConstraints = [UniqueConstraint(
        name = "uk_fcm_token",
        columnNames = ["token"]
    )]
)
class FcmToken (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_token_seq")
    val fcmTokenSeq: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false)
    var user: User,

    @Column(name = "token", nullable = false, length = 255)
    var token: String,

    @Column(name = "push_enabled", nullable = false)
    var pushEnabled: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

) {
    fun updatePushEnabled(pushEnabled: Boolean) {
        this.pushEnabled = pushEnabled
        this.updatedAt = LocalDateTime.now()
    }

    fun updateUser(newUser: User) {
        this.user.fcmTokens.remove(this)
        this.user = newUser
        newUser.fcmTokens.add(this)
    }
}
