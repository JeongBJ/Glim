package com.jeongbj.domain.setting.usecase

import com.jeongbj.domain.setting.model.Settings
import kotlinx.coroutines.flow.Flow

interface UpdateSettingsUseCase {
    suspend operator fun invoke(settings: Settings)
}

interface GetSettingsUseCase {
    operator fun invoke(): Flow<Settings>
}