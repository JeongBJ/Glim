package com.jeongbj.data.user.mapper

import com.jeongbj.data.user.response.UserResponse
import com.jeongbj.domain.user.model.User

fun UserResponse.toDomain() : User = User(
    nickname = nickname ?: "",
    imageUrl = imageUrl
)