package com.jeongbj.data.login.di

import com.jeongbj.data.login.repository.LoginRepositoryImpl
import com.jeongbj.data.login.usecase.GoogleLoginUseCaseImpl
import com.jeongbj.data.login.usecase.KakaoLoginUseCaseImpl
import com.jeongbj.domain.login.repository.LoginRepository
import com.jeongbj.domain.login.usecase.GoogleLoginUseCase
import com.jeongbj.domain.login.usecase.KakaoLoginUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindGoogleLoginUseCase(impl: GoogleLoginUseCaseImpl): GoogleLoginUseCase

    @Binds
    abstract fun bindKakaoLoginUseCase(impl: KakaoLoginUseCaseImpl): KakaoLoginUseCase
}