package com.jeongbj.presentation.feature.glim

import com.jeongbj.domain.quote.model.Quote

data class GlimState(
    val isLoading: Boolean = false
)

sealed interface GlimAction {
    data class OnLikeClicked(val quote: Quote): GlimAction
    data class OnShareClicked(val quote: Quote): GlimAction
    data class OnBookInfoClicked(val isbn13: String): GlimAction
}

sealed interface GlimSideEffect {
    data class NavigateToBookDetail(val isbn13: String): GlimSideEffect
}