package com.jeongbj.data.user.di


import com.jeongbj.data.user.repository.InfoRepositoryImpl
import com.jeongbj.data.user.usecase.info.GetLikedQuotesUseCaseImpl
import com.jeongbj.data.user.usecase.info.GetMyQuotesUseCaseImpl
import com.jeongbj.data.user.usecase.info.GetUserInfoUseCaseImpl
import com.jeongbj.data.user.usecase.info.GetUserLikedQuotesUseCaseImpl
import com.jeongbj.data.user.usecase.info.GetUserQuotesUseCaseImpl
import com.jeongbj.domain.user.repository.InfoRepository
import com.jeongbj.domain.user.usecase.GetLikedQuotesUseCase
import com.jeongbj.domain.user.usecase.GetMyQuotesUseCase
import com.jeongbj.domain.user.usecase.GetUserInfoUseCase
import com.jeongbj.domain.user.usecase.GetUserLikedQuotesUseCase
import com.jeongbj.domain.user.usecase.GetUserQuotesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InfoModule {
    @Binds
    @Singleton
    abstract fun bindInfoRepository(impl: InfoRepositoryImpl): InfoRepository

    @Binds
    abstract fun bindGetUserInfoUseCase(impl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds
    abstract fun bindGetLikedQuotesUseCase(impl: GetLikedQuotesUseCaseImpl): GetLikedQuotesUseCase

    @Binds
    abstract fun bindGetMyQuotesUseCase(impl: GetMyQuotesUseCaseImpl): GetMyQuotesUseCase

    @Binds
    abstract fun bindGetUserLikedQuotesUseCase(impl: GetUserLikedQuotesUseCaseImpl): GetUserLikedQuotesUseCase

    @Binds
    abstract fun bindGetUserQuotesUseCase(impl: GetUserQuotesUseCaseImpl): GetUserQuotesUseCase
}