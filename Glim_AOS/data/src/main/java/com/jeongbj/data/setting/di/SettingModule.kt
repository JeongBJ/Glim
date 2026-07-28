package com.jeongbj.data.setting.di

import com.jeongbj.data.setting.repository.SettingRepositoryImpl
import com.jeongbj.data.setting.usecase.GetSettingUseCaseImpl
import com.jeongbj.data.setting.usecase.UpdateSettingUseCaseImpl
import com.jeongbj.domain.setting.repository.SettingRepository
import com.jeongbj.domain.setting.usecase.GetSettingsUseCase
import com.jeongbj.domain.setting.usecase.UpdateSettingsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {
    @Binds
    @Singleton
    abstract fun bindSettingRepository(impl: SettingRepositoryImpl): SettingRepository

    @Binds
    abstract fun bindUpdateSettingUseCase(impl: UpdateSettingUseCaseImpl): UpdateSettingsUseCase

    @Binds
    abstract fun bindGetSettingUseCase(impl: GetSettingUseCaseImpl): GetSettingsUseCase
}