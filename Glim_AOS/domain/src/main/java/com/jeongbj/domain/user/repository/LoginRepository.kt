package com.jeongbj.domain.user.repository

import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.user.model.LoginToken
import com.jeongbj.domain.user.model.User

interface LoginRepository {
    suspend fun googleLogin(loginToken: LoginToken) : User

    suspend fun kakaoLogin(loginToken: LoginToken) : User
}