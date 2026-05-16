package com.jeongbj.data.user.repository

import com.jeongbj.core.common.unwrap
import com.jeongbj.data.auth.manager.TokenManager
import com.jeongbj.data.auth.mapper.toDomain
import com.jeongbj.data.user.datasource.LoginRemoteDataSource
import com.jeongbj.data.user.mapper.toDomain
import com.jeongbj.data.user.mapper.toRequest
import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.user.model.LoginToken
import com.jeongbj.domain.user.model.User
import com.jeongbj.domain.user.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val tokenManager: TokenManager
) : LoginRepository {
    override suspend fun googleLogin(loginToken: LoginToken): User {
        val response = loginRemoteDataSource.googleLogin(loginToken.toRequest()).unwrap()
        tokenManager.saveTokens(response.token.toDomain())
        return response.user.toDomain()
    }

    override suspend fun kakaoLogin(loginToken: LoginToken): User {
        val response = loginRemoteDataSource.kakaoLogin(loginToken.toRequest()).unwrap()
        tokenManager.saveTokens(response.token.toDomain())
        return response.user.toDomain()
    }
}