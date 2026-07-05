package com.jeongbj.domain.setting.repository

import com.jeongbj.domain.setting.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    suspend fun updateSettings(settings: Settings)

    fun getSettings(): Flow<Settings>

}