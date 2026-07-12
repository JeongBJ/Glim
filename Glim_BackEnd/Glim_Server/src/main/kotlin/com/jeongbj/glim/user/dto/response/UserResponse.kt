package com.jeongbj.glim.user.dto.response

data class UserResponse(
    val userSeq: Long,
    val email: String,
    val nickname: String?,
    val imageUrl: String?
)
