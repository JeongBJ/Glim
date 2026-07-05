package com.jeongbj.domain.setting.usecase

import javax.inject.Inject

data class SettingUseCases @Inject constructor(
    val updateSettingsUseCase: UpdateSettingsUseCase,
    val getSettingsUseCase: GetSettingsUseCase
)
