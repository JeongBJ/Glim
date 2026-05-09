package com.jeongbj.core.session

import kotlinx.coroutines.flow.SharedFlow

interface SessionManager {
    val sessionEvent: SharedFlow<SessionEvent>
    suspend fun notifySessionExpired()
}

