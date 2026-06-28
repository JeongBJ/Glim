package com.jeongbj.presentation.feature.info.viewmodel

import androidx.lifecycle.ViewModel
import com.jeongbj.presentation.feature.info.InfoAction
import com.jeongbj.presentation.feature.info.InfoSideEffect
import com.jeongbj.presentation.feature.info.InfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(InfoState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<InfoSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: InfoAction) {
        when (action) {

            else -> {}
        }
    }
}