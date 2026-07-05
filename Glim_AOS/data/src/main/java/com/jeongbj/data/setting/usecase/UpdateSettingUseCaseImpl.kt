package com.jeongbj.data.setting.usecase

import com.jeongbj.domain.setting.model.Settings
import com.jeongbj.domain.setting.repository.SettingRepository
import com.jeongbj.domain.setting.usecase.UpdateSettingsUseCase
import javax.inject.Inject

class UpdateSettingUseCaseImpl @Inject constructor(
    private val settingRepository: SettingRepository
): UpdateSettingsUseCase {
    override suspend fun invoke(settings: Settings) =
        settingRepository.updateSettings(settings)
}