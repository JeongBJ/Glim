package com.jeongbj.glim.user.mapper

import com.jeongbj.glim.quote.dto.QuoteUserResponse
import com.jeongbj.glim.user.dto.response.UserResponse
import com.jeongbj.glim.user.entity.User

fun User.toResponse(): UserResponse =
    UserResponse(
        userSeq = userSeq,
        email = email,
        nickname = nickname,
        imageUrl = imageUrl
    )

fun User.toQuoteResponse(): QuoteUserResponse =
    QuoteUserResponse(
        userSeq = userSeq,
        nickname = nickname ?: "알 수 없음",
        imageUrl = imageUrl ?: ""
    )