package com.jeongbj.presentation.feature.info.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.ac
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.user.usecase.GetUserInfoUseCase
import com.jeongbj.presentation.feature.info.GlimType
import com.jeongbj.presentation.feature.info.InfoAction
import com.jeongbj.presentation.feature.info.InfoSideEffect
import com.jeongbj.presentation.feature.info.InfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(InfoState())
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<InfoSideEffect>(extraBufferCapacity = 1)
    val sideEffect = _sideEffect.asSharedFlow()

    fun onAction(action: InfoAction) {
        when (action) {
            InfoAction.OnProfileImageClicked -> onProfileImageClicked()
            is InfoAction.OnQuoteThumbnailClicked -> onQuoteThumbnailClicked(action.quoteSeq)
            is InfoAction.OnTabSelected -> onTabSelected(action.tab)
        }
    }

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase().collect { result ->
                when (result) {
                    is ResultType.Success -> _state.update { it.copy(userInfo = result.data) }
                    is ResultType.Error -> {
                        Timber.d("getUserInfo: $result")
                    }
                    ResultType.Loading -> {}
                }
            }
        }
    }

    private fun onTabSelected(tab: GlimType) {
        _state.update { it.copy(selectedTab = tab) }
    }

    private fun onQuoteThumbnailClicked(quoteSeq: Long) =
        _sideEffect.tryEmit(InfoSideEffect.NavigateToQuoteDetail(quoteSeq))

    private fun onProfileImageClicked() =
        _sideEffect.tryEmit(InfoSideEffect.NavigateToProfile(_state.value.userInfo?.user))
}