package com.jeongbj.presentation.feature.info

import com.jeongbj.domain.user.model.User
import com.jeongbj.presentation.R
import java.time.LocalDate

data class InfoState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val contributions: HashMap<LocalDate, Int> = hashMapOf(),
    val selectedTab: GlimType = GlimType.OWN
)

sealed interface InfoAction {
    data object OnProfileImageClicked: InfoAction
    data class OnTabSelected(val tab: GlimType): InfoAction
    data class OnQuoteThumbnailClicked(val quoteSeq: Long): InfoAction

}

sealed interface InfoSideEffect {

}

enum class GlimType(val resId: Int) {
    OWN(R.drawable.ic_book_ribbon),
    LIKED(R.drawable.ic_like_200_fill)
}