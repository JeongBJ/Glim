package com.jeongbj.presentation.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jeongbj.presentation.feature.settings.viewmodel.SettingViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {

                else -> {}
            }
        }
    }

    SettingContent(
        state = state,
        onAction = { viewModel.onAction(it) }
    )
}