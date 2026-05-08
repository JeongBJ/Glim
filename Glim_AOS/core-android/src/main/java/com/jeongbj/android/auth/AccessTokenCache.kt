package com.jeongbj.android.auth

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenCache @Inject constructor() {

    @Volatile
    private var accessToken: String? = null

    fun get(): String? = accessToken

    fun set(token: String?) {
        accessToken = token
    }

    fun clear() {
        accessToken = null
    }
}