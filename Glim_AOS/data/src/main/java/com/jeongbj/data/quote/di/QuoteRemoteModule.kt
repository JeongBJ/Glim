package com.jeongbj.data.quote.di

import com.jeongbj.data.quote.api.QuoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object QuoteRemoteModule {

    @Provides
    fun provideQuoteApi(retrofit: Retrofit): QuoteApi = retrofit.create(QuoteApi::class.java)
}