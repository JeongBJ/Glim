package com.jeongbj.data.network.interceptor

import com.jeongbj.core.common.JWT
import com.jeongbj.core.common.ResultType
import com.jeongbj.core.session.SessionManager
import com.jeongbj.domain.auth.repository.AuthRepository
import com.jeongbj.domain.auth.storage.AccessTokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authRepository: Provider<AuthRepository>,
    private val accessTokenStorage: AccessTokenStorage,
    private val sessionManager: SessionManager
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null

        return runBlocking {
            mutex.withLock {
                val currentToken = accessTokenStorage.getAccessToken()
                val requestToken = response.request.header(JWT.HEADER)

                val newToken = if (requestToken != "${JWT.TYPE} $currentToken") {
                    currentToken
                } else {
                    val result = authRepository.get().refreshAccessToken().first { it !is ResultType.Loading }
                    if (result is ResultType.Success) result.data.accessToken
                    else {
                        sessionManager.notifySessionExpired()
                        null
                    }
                }

                if (newToken == null) return@runBlocking null

                response.request.newBuilder()
                    .header(JWT.HEADER, "${JWT.TYPE} $newToken")
                    .build()

            }
        }
    }
}