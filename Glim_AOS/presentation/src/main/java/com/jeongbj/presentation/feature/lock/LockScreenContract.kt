package com.jeongbj.presentation.feature.lock

import java.time.LocalDateTime

data class LockScreenState(
    val time: LocalDateTime = LocalDateTime.now(),
    val isLoading: Boolean = false
)

sealed interface LockScreenAction {
    data class OnSaveClicked(val imageUrl: String): LockScreenAction
    data class OnOpenGlimClicked(val quoteSeq: Long): LockScreenAction
    data object OnOpenCameraClicked: LockScreenAction
    data object OnUnlocked: LockScreenAction
}

sealed interface LockScreenSideEffect {
    data object OpenCamera : LockScreenSideEffect
    data class OpenQuote(val quoteSeq: Long): LockScreenSideEffect
    data object UnlockScreen : LockScreenSideEffect
    data class ShowToast(val msg: String): LockScreenSideEffect
}