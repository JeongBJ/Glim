package com.jeongbj.domain.user.model

data class User(
    val userSeq: Long = 0,
    val nickname: String,
    val imageUrl: String? = null
)
