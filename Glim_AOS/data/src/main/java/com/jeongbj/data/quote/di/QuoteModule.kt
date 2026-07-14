package com.jeongbj.data.quote.di

import com.jeongbj.data.quote.repository.QuoteRepositoryImpl
import com.jeongbj.data.quote.usecase.DeleteQuoteUseCaseImpl
import com.jeongbj.data.quote.usecase.GenerateImageUseCaseImpl
import com.jeongbj.data.quote.usecase.GetQuoteUseCaseImpl
import com.jeongbj.data.quote.usecase.GetQuotesUseCaseImpl
import com.jeongbj.data.quote.usecase.IncreaseViewUseCaseImpl
import com.jeongbj.data.quote.usecase.LikeQuoteUseCaseImpl
import com.jeongbj.data.quote.usecase.SaveQuoteUseCaseImpl
import com.jeongbj.domain.quote.repository.QuoteRepository
import com.jeongbj.domain.quote.usecase.DeleteQuoteUseCase
import com.jeongbj.domain.quote.usecase.GenerateImageUseCase
import com.jeongbj.domain.quote.usecase.GetQuoteUseCase
import com.jeongbj.domain.quote.usecase.GetQuotesUseCase
import com.jeongbj.domain.quote.usecase.IncreaseViewUseCase
import com.jeongbj.domain.quote.usecase.LikeQuoteUseCase
import com.jeongbj.domain.quote.usecase.SaveQuoteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QuoteModule {

    @Binds
    @Singleton
    abstract fun bindQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository

    @Binds
    abstract fun bindGenerateImageUseCase(impl: GenerateImageUseCaseImpl): GenerateImageUseCase

    @Binds
    abstract fun bindSaveQuoteUseCase(impl: SaveQuoteUseCaseImpl): SaveQuoteUseCase

    @Binds
    abstract fun bindGetQuotesUseCase(impl: GetQuotesUseCaseImpl): GetQuotesUseCase

    @Binds
    abstract fun bindGetQuoteUseCase(impl: GetQuoteUseCaseImpl): GetQuoteUseCase

    @Binds
    abstract fun bindIncreaseViewUseCase(impl: IncreaseViewUseCaseImpl): IncreaseViewUseCase

    @Binds
    abstract fun likeQuoteUseCase(impl: LikeQuoteUseCaseImpl): LikeQuoteUseCase

    @Binds
    abstract fun deleteQuoteUseCase(impl: DeleteQuoteUseCaseImpl): DeleteQuoteUseCase
}