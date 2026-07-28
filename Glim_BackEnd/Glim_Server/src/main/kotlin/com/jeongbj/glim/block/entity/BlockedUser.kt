package com.jeongbj.glim.block.entity

import com.jeongbj.glim.user.entity.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "t_blocked_user",
    uniqueConstraints = [UniqueConstraint(
        name = "uk_blocked_user_blocked_user",
        columnNames = ["user_seq", "blocked_user_seq"]
    )],
    indexes = [
        Index(name = "idx_blocked_user_user_blocked", columnList = "user_seq, blocked_user_id")
    ]
)
class BlockedUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val blockedUserId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_user_seq")
    val blockedUser: User,

    val createdAt: LocalDateTime = LocalDateTime.now()
)