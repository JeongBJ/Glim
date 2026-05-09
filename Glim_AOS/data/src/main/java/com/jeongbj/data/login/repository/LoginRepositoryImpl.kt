package com.jeongbj.data.login.repository

import com.jeongbj.core.common.unwrap
import com.jeongbj.data.auth.mapper.toDomain
import com.jeongbj.data.login.datasource.LoginRemoteDataSource
import com.jeongbj.data.login.mapper.toRequest
import com.jeongbj.domain.auth.model.AuthToken
import com.jeongbj.domain.login.model.LoginToken
import com.jeongbj.domain.login.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
) : LoginRepository {
    override suspend fun googleLogin(loginToken: LoginToken): AuthToken {
        return loginRemoteDataSource.googleLogin(loginToken.toRequest())
            .unwrap()
            .toDomain()
    }

    override suspend fun kakaoLogin(loginToken: LoginToken): AuthToken {
        return loginRemoteDataSource.kakaoLogin(loginToken.toRequest())
            .unwrap()
            .toDomain()
    }
}