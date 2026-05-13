package com.jeongbj.glim.user.mapper

import com.jeongbj.glim.user.dto.response.UserResponse
import com.jeongbj.glim.user.entity.User

fun User.toResponse(): UserResponse =
    UserResponse(
        email = email,
        nickname = nickname,
        imageUrl = imageUrl
    )