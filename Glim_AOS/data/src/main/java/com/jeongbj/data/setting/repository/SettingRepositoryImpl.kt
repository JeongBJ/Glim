package com.jeongbj.data.setting.repository

import com.jeongbj.data.setting.datasource.SettingLocalDataSource
import com.jeongbj.domain.setting.model.Settings
import com.jeongbj.domain.setting.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingLocalDataSource: SettingLocalDataSource
): SettingRepository {
    override suspend fun updateSettings(settings: Settings) =
        settingLocalDataSource.update(settings)

    override fun getSettings(): Flow<Settings> =
        settingLocalDataSource.get()
}