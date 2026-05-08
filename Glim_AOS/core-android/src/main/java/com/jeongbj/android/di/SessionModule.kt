package com.jeongbj.android.di

import com.jeongbj.android.session.SessionManagerImpl
import com.jeongbj.core.session.SessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager
}