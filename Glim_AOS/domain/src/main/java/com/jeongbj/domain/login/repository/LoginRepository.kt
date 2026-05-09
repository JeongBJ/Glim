package com.jeongbj.domain.login.repository

import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.login.model.LoginToken

interface LoginRepository {
    suspend fun googleLogin(loginToken: LoginToken) : AuthToken

    suspend fun kakaoLogin(loginToken: LoginToken) : AuthToken
}