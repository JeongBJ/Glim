package com.jeongbj.data.block.di

import com.jeongbj.data.block.repository.BlockRepositoryImpl
import com.jeongbj.data.block.usecase.BlockQuoteUseCaseImpl
import com.jeongbj.data.block.usecase.BlockUserUseCaseImpl
import com.jeongbj.data.block.usecase.GetBlockedQuotesUseCaseImpl
import com.jeongbj.data.block.usecase.GetBlockedUsersUseCaseImpl
import com.jeongbj.data.block.usecase.UnblockQuoteUseCaseImpl
import com.jeongbj.data.block.usecase.UnblockUserUseCaseImpl
import com.jeongbj.domain.block.repository.BlockRepository
import com.jeongbj.domain.block.usecase.BlockQuoteUseCase
import com.jeongbj.domain.block.usecase.BlockUserUseCase
import com.jeongbj.domain.block.usecase.GetBlockedQuotesUseCase
import com.jeongbj.domain.block.usecase.GetBlockedUsersUseCase
import com.jeongbj.domain.block.usecase.UnblockQuoteUseCase
import com.jeongbj.domain.block.usecase.UnblockUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BlockModule {

    @Binds
    @Singleton
    abstract fun bindBlockRepository(impl: BlockRepositoryImpl): BlockRepository

    @Binds
    abstract fun bindBlockQuoteUseCase(impl: BlockQuoteUseCaseImpl): BlockQuoteUseCase

    @Binds
    abstract fun bindBlockUserUseCase(impl: BlockUserUseCaseImpl): BlockUserUseCase

    @Binds
    abstract fun bindUnblockQuoteUseCase(impl: UnblockQuoteUseCaseImpl): UnblockQuoteUseCase

    @Binds
    abstract fun bindUnblockUserUseCase(impl: UnblockUserUseCaseImpl): UnblockUserUseCase

    @Binds
    abstract fun bindGetBlockedQuotesUseCase(impl: GetBlockedQuotesUseCaseImpl): GetBlockedQuotesUseCase

    @Binds
    abstract fun bindGetBlockedUsersUseCase(impl: GetBlockedUsersUseCaseImpl): GetBlockedUsersUseCase

}