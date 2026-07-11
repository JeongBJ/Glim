package com.jeongbj.presentation.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.jeongbj.android.extentions.showToast
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.settings.viewmodel.SettingViewModel
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SettingSideEffect.NavigateBack -> navigateBack()
                SettingSideEffect.NavigateLogin -> navigateToLogin()
                is SettingSideEffect.ShowToast -> context.showToast(effect.message)
            }
        }
    }

    SettingContent(
        state = state,
        onAction = { viewModel.onAction(it) }
    )
}

@Previews
@Composable
fun SettingScreenPreview() {
    GlimTheme {
        SettingContent(
            state = SettingState(),
            onAction = { }
        )
    }
}