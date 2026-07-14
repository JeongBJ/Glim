package com.jeongbj.data.block.di

import com.jeongbj.data.block.api.BlockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object BlockRemoteModule {

    @Provides
    fun provideBlockApi(retrofit: Retrofit): BlockApi = retrofit.create(BlockApi::class.java)

}