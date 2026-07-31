package com.jeongbj.presentation.feature.info

import com.jeongbj.domain.user.model.UserInfo
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.model.UserUI
import java.time.LocalDate

data class InfoState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isOwner: Boolean = false,
    val userSeq: Long? = null,
    val userInfo: UserInfo? = null,
    val contributions: Map<LocalDate, Int> = mapOf(),
    val selectedTab: GlimType = GlimType.OWN
)

sealed interface InfoAction {
    data object OnProfileImageClicked: InfoAction
    data class OnTabSelected(val tab: GlimType): InfoAction
    data class OnQuoteThumbnailClicked(val quoteSeq: Long): InfoAction
    data object OnSettingClicked: InfoAction
    data object OnRefresh: InfoAction
}

sealed interface InfoSideEffect {
    data class NavigateToProfile(val user: UserUI): InfoSideEffect
    data class NavigateToQuoteDetail(val quoteSeq: Long): InfoSideEffect
    data object NavigateToSettings: InfoSideEffect
}

enum class GlimType(val resId: Int) {
    OWN(R.drawable.ic_book_ribbon),
    LIKED(R.drawable.ic_like_200_fill)
}