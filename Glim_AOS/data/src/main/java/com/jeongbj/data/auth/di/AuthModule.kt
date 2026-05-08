package com.jeongbj.data.auth.di

import com.jeongbj.data.auth.repository.AuthRepositoryImpl
import com.jeongbj.data.auth.storage.AccessTokenStorageImpl
import com.jeongbj.data.auth.usecase.ClearTokenUseCaseImpl
import com.jeongbj.data.auth.usecase.LogoutUseCaseImpl
import com.jeongbj.data.auth.usecase.RefreshAccessTokenUseCaseImpl
import com.jeongbj.data.auth.usecase.ResignUseCaseImpl
import com.jeongbj.domain.auth.repository.AuthRepository
import com.jeongbj.domain.auth.storage.AccessTokenStorage
import com.jeongbj.domain.auth.usecase.ClearTokenUseCase
import com.jeongbj.domain.auth.usecase.LogoutUseCase
import com.jeongbj.domain.auth.usecase.RefreshAccessTokenUseCase
import com.jeongbj.domain.auth.usecase.ResignUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl) : AuthRepository

    @Binds
    @Singleton
    abstract fun bindAccessTokenStorage(impl: AccessTokenStorageImpl): AccessTokenStorage

    @Binds
    abstract fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    @Binds
    abstract fun bindResignUseCase(impl: ResignUseCaseImpl): ResignUseCase

    @Binds
    abstract fun bindRefreshAccessTokenUseCase(impl: RefreshAccessTokenUseCaseImpl): RefreshAccessTokenUseCase

    @Binds
    abstract fun bindClearTokenUseCase(impl: ClearTokenUseCaseImpl): ClearTokenUseCase
}