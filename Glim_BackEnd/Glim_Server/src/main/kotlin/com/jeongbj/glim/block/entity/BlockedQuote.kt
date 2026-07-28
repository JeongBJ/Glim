package com.jeongbj.glim.block.entity

import com.jeongbj.glim.quote.entity.Quote
import com.jeongbj.glim.user.entity.User
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(
    name = "t_blocked_quote",
    uniqueConstraints = [UniqueConstraint(
        name = "uk_blocked_quote_quote",
        columnNames = ["user_seq", "quote_seq"]
    )],
    indexes = [
        Index(name = "idx_blocked_quote_user_quote", columnList = "user_seq, quote_seq")
    ]
)
class BlockedQuote (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val blockedQuoteSeq: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_seq")
    val quote: Quote,

    val createdAt: LocalDateTime = LocalDateTime.now()
)