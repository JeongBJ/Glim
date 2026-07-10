package com.jeongbj.presentation.feature.settings

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.feature.settings.viewmodel.SettingViewModel
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

fun NavGraphBuilder.settingNav(
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable<SettingRoute>{
        val viewModel: SettingViewModel = hiltViewModel()

        SettingScreen(
            viewModel = viewModel,
            navigateBack = navigateBack,
            navigateToLogin = navigateToLogin
        )
    }
}