package com.jeongbj.data.user.di

import com.jeongbj.data.user.repository.LoginRepositoryImpl
import com.jeongbj.data.user.usecase.GoogleLoginUseCaseImpl
import com.jeongbj.data.user.usecase.KakaoLoginUseCaseImpl
import com.jeongbj.domain.user.repository.LoginRepository
import com.jeongbj.domain.user.usecase.GoogleLoginUseCase
import com.jeongbj.domain.user.usecase.KakaoLoginUseCase
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