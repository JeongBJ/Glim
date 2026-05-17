package com.jeongbj.presentation.feature.login.util

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class KakaoLoginLauncher(
    private val context: Context
) {
    private val userApiClient = UserApiClient.instance

    suspend fun login(): KakaoLoginResult = suspendCancellableCoroutine { continuation ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        continuation.resume(KakaoLoginResult.Cancelled)
                    } else {
                        continuation.resume(KakaoLoginResult.Error(error))
                    }
                }

                token != null -> {
                    val accessToken = token.accessToken
                    continuation.resume(KakaoLoginResult.Success(accessToken))
                }
                else -> continuation.resume(KakaoLoginResult.Error(Throwable("Unknown error")))
            }
        }

        if (userApiClient.isKakaoTalkLoginAvailable(context)) {
            userApiClient.loginWithKakaoTalk(context) { token, error ->
                when {
                    error != null -> {
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            continuation.resume(KakaoLoginResult.Cancelled)
                            return@loginWithKakaoTalk
                        }
                        userApiClient.loginWithKakaoAccount(context, callback = callback)
                    }

                    token != null -> {
                        val accessToken = token.accessToken
                        continuation.resume(KakaoLoginResult.Success(accessToken))
                    }
                }
            }
        } else {
            userApiClient.loginWithKakaoAccount(context, callback = callback)
        }
    }
}
sealed interface KakaoLoginResult {
    data class Success(
        val idToken: String
    ) : KakaoLoginResult

    data object Cancelled : KakaoLoginResult

    data class Error(
        val throwable: Throwable
    ) : KakaoLoginResult
}