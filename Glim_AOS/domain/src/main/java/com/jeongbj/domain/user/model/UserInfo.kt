package com.jeongbj.domain.user.model

data class UserInfo(
    val user: User,
    val numLikes: Int,
    val numQuotes: Int,
    val contributions: List<GlimContribution>
)
