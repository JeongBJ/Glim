package com.jeongbj.glim.like.entity

import com.jeongbj.glim.quote.entity.Quote
import com.jeongbj.glim.user.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "t_like",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_like_user_quote",
            columnNames = ["user_seq", "quote_seq"]
        )
    ])
class Like (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val likeSeq: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq", nullable = false, foreignKey = ForeignKey(name = "fk_like_user"))
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_seq", nullable = false, foreignKey = ForeignKey(name = "fk_like_quote"))
    val quote: Quote
)