package com.jeongbj.android.keystore

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideKeyStoreHelper(): KeyStoreHelper = KeyStoreHelper()

    @Provides
    @Singleton
    fun provideKeyStoreManager(helper: KeyStoreHelper): KeyStoreManager =
        KeyStoreManager(helper)
}