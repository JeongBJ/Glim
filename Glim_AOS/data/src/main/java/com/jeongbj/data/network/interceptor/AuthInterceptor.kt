package com.jeongbj.data.network.interceptor

import com.jeongbj.core.common.JWT
import com.jeongbj.domain.auth.storage.AccessTokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val accessTokenStorage: AccessTokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = accessTokenStorage.getAccessToken()
        Timber.d("intercept: $token")

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader(JWT.HEADER, "${JWT.TYPE} $token")
            }
        }.build()

        return chain.proceed(request)
    }
}