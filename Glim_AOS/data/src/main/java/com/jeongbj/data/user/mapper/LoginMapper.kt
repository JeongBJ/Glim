package com.jeongbj.data.user.mapper

import com.jeongbj.data.user.request.LoginTokenRequest
import com.jeongbj.domain.user.model.LoginToken

fun LoginToken.toRequest() : LoginTokenRequest = LoginTokenRequest (
    idToken = idToken,
    fcmToken = fcmToken
)