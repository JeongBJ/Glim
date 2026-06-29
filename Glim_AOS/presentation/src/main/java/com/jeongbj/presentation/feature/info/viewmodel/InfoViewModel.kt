package com.jeongbj.presentation.feature.info.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jeongbj.core.common.ResultType
import com.jeongbj.domain.user.model.InfoQuotesType
import com.jeongbj.domain.user.usecase.InfoUseCases
import com.jeongbj.presentation.common.model.UserUI
import com.jeongbj.presentation.common.paging.QuoteThumbnailPagingSource
import com.jeongbj.presentation.feature.info.GlimType
import com.jeongbj.presentation.feature.info.InfoAction
import com.jeongbj.presentation.feature.info.InfoRoute
import com.jeongbj.presentation.feature.info.InfoSideEffect
import com.jeongbj.presentation.feature.info.InfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class InfoViewModel @Inject constructor(
    private val infoUseCases: InfoUseCases,
    savedStateHandle: SavedStateHandle
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
            InfoAction.OnSettingClicked -> onSettingClicked()
        }
    }

    private fun onSettingClicked() =
        _sideEffect.tryEmit(InfoSideEffect.NavigateToSettings)

    private val quoteTrigger = MutableStateFlow<InfoRequest>(
        InfoRequest(InfoQuotesType.MY, null)
    )
    val quotes = quoteTrigger.flatMapLatest { request ->
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                QuoteThumbnailPagingSource(
                    infoUseCases = infoUseCases,
                    type = request.type,
                    userSeq = request.userSeq
                )
            }
        ).flow
    }.cachedIn(viewModelScope)

    init {
        getUserInfo()
        val userSeq = savedStateHandle.toRoute<InfoRoute>().userSeq
        if (userSeq == null) {
            _state.update { it.copy(isOwner = true) }
            getQuoteThumbnails()
        } else {
            _state.update { it.copy(userSeq = userSeq) }
            getQuoteThumbnails()
        }
    }

    private fun getQuoteThumbnails() {
        val state = _state.value
        val type = when (state.selectedTab) {
            GlimType.OWN -> {
                if (state.isOwner) InfoQuotesType.MY
                else InfoQuotesType.USER
            }
            GlimType.LIKED -> {
                if (state.isOwner) InfoQuotesType.LIKED
                else InfoQuotesType.USER_LIKED
            }
        }
        quoteTrigger.tryEmit(InfoRequest(type, state.userSeq))
    }
    private fun getUserInfo() {
        viewModelScope.launch {
            infoUseCases.getUserInfoUseCase().collect { result ->
                when (result) {
                    is ResultType.Success -> {
                        val map = result.data.contributions.associate { it.date to it.count}
                        _state.update { it.copy(
                            userInfo = result.data,
                            contributions = map
                        ) }
                    }
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
        getQuoteThumbnails()
    }

    private fun onQuoteThumbnailClicked(quoteSeq: Long) =
        _sideEffect.tryEmit(InfoSideEffect.NavigateToQuoteDetail(quoteSeq))

    private fun onProfileImageClicked() {
        val user = _state.value.userInfo?.user ?: return
        _sideEffect.tryEmit(InfoSideEffect.NavigateToProfile(
            UserUI(
                nickname = user.nickname,
                imageUrl = user.imageUrl
            )
        ))
    }

    private data class InfoRequest(
        val type: InfoQuotesType,
        val userSeq: Long? = null
    )
}