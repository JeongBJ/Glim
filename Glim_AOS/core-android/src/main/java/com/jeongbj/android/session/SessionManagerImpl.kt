package com.jeongbj.android.session

import com.jeongbj.core.session.SessionEvent
import com.jeongbj.core.session.SessionManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor() : SessionManager {
    private val _sessionEvent = MutableSharedFlow<SessionEvent>(extraBufferCapacity = 1)
    override val sessionEvent get() = _sessionEvent.asSharedFlow()

    override suspend fun notifySessionExpired() {
        _sessionEvent.tryEmit(SessionEvent.Expired)
    }
}