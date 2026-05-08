package com.jeongbj.data.auth.storage

import com.jeongbj.android.auth.AccessTokenCache
import com.jeongbj.domain.auth.storage.AccessTokenStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenStorageImpl @Inject constructor(
    private val accessTokenCache: AccessTokenCache
) : AccessTokenStorage {
    override fun getAccessToken() = accessTokenCache.get()

    override fun setAccessToken(token: String) = accessTokenCache.set(token)
    override fun clear() = accessTokenCache.clear()
}