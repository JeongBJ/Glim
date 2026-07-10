package com.jeongbj.data.user.di

import com.jeongbj.data.user.repository.LoginRepositoryImpl
import com.jeongbj.data.user.repository.UserRepositoryImpl
import com.jeongbj.data.user.usecase.AutoLoginUseCaseImpl
import com.jeongbj.data.user.usecase.GoogleLoginUseCaseImpl
import com.jeongbj.data.user.usecase.KakaoLoginUseCaseImpl
import com.jeongbj.data.user.usecase.UpdateFcmTokenUseCaseImpl
import com.jeongbj.data.user.usecase.UpdateProfileUseCaseImpl
import com.jeongbj.domain.user.repository.LoginRepository
import com.jeongbj.domain.user.repository.UserRepository
import com.jeongbj.domain.user.usecase.AutoLoginUseCase
import com.jeongbj.domain.user.usecase.GoogleLoginUseCase
import com.jeongbj.domain.user.usecase.KakaoLoginUseCase
import com.jeongbj.domain.user.usecase.UpdateFcmTokenUseCase
import com.jeongbj.domain.user.usecase.UpdateProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindGoogleLoginUseCase(impl: GoogleLoginUseCaseImpl): GoogleLoginUseCase

    @Binds
    abstract fun bindKakaoLoginUseCase(impl: KakaoLoginUseCaseImpl): KakaoLoginUseCase

    @Binds
    abstract fun bindAutoLoginUseCase(impl: AutoLoginUseCaseImpl): AutoLoginUseCase

    @Binds
    abstract fun bindUpdateProfileUseCase(impl: UpdateProfileUseCaseImpl): UpdateProfileUseCase

    @Binds
    abstract fun bindUpdateFcmTokenUseCase(impl: UpdateFcmTokenUseCaseImpl): UpdateFcmTokenUseCase
}