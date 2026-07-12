package com.jeongbj.presentation.feature.glim

import com.jeongbj.domain.quote.model.Quote

data class GlimState(
    val isLoading: Boolean = false,
    val currentUserSeq: Long = 0
)

sealed interface GlimAction {
    data class OnLikeClicked(val quote: Quote): GlimAction
    data class OnShareClicked(val quote: Quote): GlimAction
    data class OnBookInfoClicked(val isbn13: String): GlimAction
    data class OnSaveClicked(val imageUrl: String): GlimAction
    data class OnProfileClicked(val userSeq: Long): GlimAction

}

sealed interface GlimSideEffect {
    data class NavigateToBookDetail(val isbn13: String): GlimSideEffect
    data class NavigateToInfo(val userSeq: Long): GlimSideEffect
    data class ShowToast(val msg: String): GlimSideEffect
    data class ShareGlim(val link: String): GlimSideEffect
}