package com.jeongbj.data.network.interceptor

import com.jeongbj.core.common.JWT
import com.jeongbj.domain.auth.manager.TokenManager
import com.jeongbj.domain.auth.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authRepository: Provider<AuthRepository>,
    private val tokenManager: TokenManager
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null

        return runBlocking {
            mutex.withLock {

                val refreshToken = tokenManager.getRefreshToken()
                    ?: return@runBlocking null

                val newToken = try {
                    authRepository.get()
                        .refreshAccessToken(refreshToken)
                } catch (e: Exception) {
                    Timber.tag("TokenAuthenticator").e(e, "authenticate: ")
                    tokenManager.notifyTokenExpired()
                    return@runBlocking null
                }

                tokenManager.saveTokens(newToken)

                response.request.newBuilder()
                    .header(JWT.HEADER, "${JWT.TYPE} ${newToken.accessToken}")
                    .build()

            }
        }
    }
}