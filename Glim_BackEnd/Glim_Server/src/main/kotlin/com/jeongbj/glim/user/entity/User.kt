package com.jeongbj.glim.user.entity

import com.jeongbj.glim.quote.entity.Quote
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "t_user",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_provider_email",
            columnNames = ["provider", "email"]
        )
    ]
)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    val userSeq: Long = 0,

    @Column(name = "email", nullable = false)
    val email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    val provider: Provider,

    @Column(name = "nickname")
    var nickname: String? = null,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val fcmTokens: MutableList<FcmToken> = mutableListOf(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val quotes: MutableList<Quote> = mutableListOf()

) {
    fun updateProfile(nickname: String, imageUrl: String?): User {
        this.nickname = nickname
        this.imageUrl = imageUrl
        return this
    }
}


enum class Provider {
    GOOGLE,
    KAKAO
}