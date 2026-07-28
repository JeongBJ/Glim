package com.jeongbj.data.book.di

import com.jeongbj.data.book.api.BookApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object BookRemoteModule {

    @Provides
    fun provideBookApi(retrofit: Retrofit): BookApi = retrofit.create(BookApi::class.java)
}