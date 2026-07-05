package com.jeongbj.data.setting.usecase

import com.jeongbj.domain.setting.model.Settings
import com.jeongbj.domain.setting.repository.SettingRepository
import com.jeongbj.domain.setting.usecase.GetSettingsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingUseCaseImpl @Inject constructor(
    private val settingRepository: SettingRepository
): GetSettingsUseCase {
    override fun invoke(): Flow<Settings> =
        settingRepository.getSettings()

}