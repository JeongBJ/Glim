package com.jeongbj.data.user.mapper

import com.jeongbj.data.fcm.mapper.toRequest
import com.jeongbj.data.user.request.LoginTokenRequest
import com.jeongbj.domain.user.model.FcmToken
import com.jeongbj.domain.user.model.LoginToken

fun LoginToken.toRequest(fcmToken: FcmToken?) : LoginTokenRequest = LoginTokenRequest (
    idToken = idToken,
    fcmTokenRequest = fcmToken?.toRequest()
)
