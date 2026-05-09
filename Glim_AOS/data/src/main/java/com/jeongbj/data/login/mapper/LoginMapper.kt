package com.jeongbj.data.login.mapper

import com.jeongbj.data.login.request.LoginTokenRequest
import com.jeongbj.domain.login.model.LoginToken

fun LoginToken.toRequest() : LoginTokenRequest = LoginTokenRequest (
    idToken = idToken,
    fcmToken = fcmToken
)