package com.jeongbj.data.fcm.mapper

import com.jeongbj.data.fcm.request.FcmTokenRequest
import com.jeongbj.domain.user.model.FcmToken

fun FcmToken.toRequest() : FcmTokenRequest = FcmTokenRequest(
    token = token,
    enabled = enabled
)

fun FcmTokenRequest.toDomain() : FcmToken = FcmToken(
    token = token,
    enabled = enabled
)