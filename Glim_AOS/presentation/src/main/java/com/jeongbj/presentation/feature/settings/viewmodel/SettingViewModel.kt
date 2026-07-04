package com.jeongbj.presentation.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.jeongbj.presentation.feature.settings.SettingAction
import com.jeongbj.presentation.feature.settings.SettingSideEffect
import com.jeongbj.presentation.feature.settings.SettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SettingSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: SettingAction) {
        when (action) {

            else -> {}
        }
    }
}